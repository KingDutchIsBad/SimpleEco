package gitlab.kingdutchisbad.user.simpleeconomy.commands;

import gitlab.kingdutchisbad.user.simpleeconomy.Data;
import gitlab.kingdutchisbad.user.simpleeconomy.SimpleEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class eco  implements CommandExecutor, TabCompleter {
    private SimpleEconomy plugin = SimpleEconomy.getPlugin(SimpleEconomy.class);

    @Override
    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("eco")) {
            try {
                if (args[0].equalsIgnoreCase("deposit")) {
                    if (player.hasPermission("SimpleEconomy.admin.deposit")) {
                        if (args.length == 3) {
                            try {
                                if (args[1] == null) {
                                    player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco deposit <Player> <money>");
                                    return true;
                                }
                                if (args[2] == null) {
                                    player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco deposit <Player> <money>");
                                    return true;
                                }
                                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                if (target == null) {
                                    player.sendMessage(thumbNail() + ChatColor.RED + " That Player Doesn't exist");
                                    return true;
                                }
                                Double withdraw = getDouble(args[2]);
                                plugin.managers.depositPlayer(target, withdraw);
                                player.sendMessage(thumbNail() + ChatColor.GRAY + " You have deposited §a$" + withdraw + "§7 into §a" + target.getName() + "'s §7account");


                            } catch (NumberFormatException exception) {
                                //hehehehe hacky way go brrr
                                player.sendMessage(thumbNail() + ChatColor.RED + " Error: Too much money cannot deposit that");
                            }
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("balance")) {
                    try {
                        if (player.hasPermission("SimpleEconomy.use.Balance")) {
                            if (args.length == 2) {
                                try {
                                    if (args[1] == null) return true;
                                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                    if (target == null) {
                                        player.sendMessage(thumbNail() + ChatColor.RED + " That player doesn't exist");
                                        return true;
                                    }
                                    player.sendMessage(thumbNail() + ChatColor.GREEN + target.getName() + " §7has §a$" + plugin.managers.getBalance(target) + "§7 in their account");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (player instanceof Player) {
                                Player player1 = (Player) player;
                                player.sendMessage(thumbNail() + ChatColor.GREEN + player.getName() + " §7has §a$" + plugin.managers.getBalance(player1) + "§7 in their account");
                            }
                            return true;
                        } else {
                            player.sendMessage(thumbNail() + ChatColor.RED + " You don't have the permission to use this command");
                        }
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco balance");
                        player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco balance <Player>");
                    }

                }

                if (args[0].equalsIgnoreCase("remove")) {
                    if (player.hasPermission("SimpleEconomy.admin.remove")) {
                        try {
                            try {
                                if (args.length == 3) {
                                    if (args[1] == null) {
                                        player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco remove <Player> <money>");
                                        return true;
                                    }
                                    if (args[2] == null) {
                                        player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco remove <Player> <money>");
                                        return true;
                                    }
                                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                    Double withdraw = getDouble(args[2]);
                                    if (target == null) {
                                        player.sendMessage(ChatColor.RED + "That player doesn't exist");
                                        return true;
                                    }
                                    plugin.managers.withdrawPlayer(target, withdraw);
                                    player.sendMessage(thumbNail() + ChatColor.GREEN + target.getName() + " now has " + plugin.managers.getBalance(target));
                                } else {
                                    sendMessage(player, thumbNail() + "&4Usage: /eco remove <player> <money?");
                                }
                            } catch (NumberFormatException exception) {
                                //hahaha hacky way go brrrrr
                                player.sendMessage(thumbNail() + ChatColor.RED + " Error: Too much money cannot remove that");
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }

                if (player instanceof Player) {
                    if (args[0].equalsIgnoreCase("pay")) {
                        try {
                            try {
                                if (args.length == 3) {
                                    if (player.hasPermission("SimpleEconomy.use.pay")) {
                                        Player player1 = (Player) player;
                                        if (args[1] == null) {
                                            player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco pay <Player> <money>");
                                            return true;
                                        }
                                        if (args[2] == null) {
                                            player.sendMessage(thumbNail() + ChatColor.RED + " Usage: /eco pay <Player> <money>");
                                            return true;
                                        }
                                        Player target = Bukkit.getPlayer(args[1]);
                                        if (args[2].isEmpty()) return true;
                                        Double payment = getDouble(args[2]).doubleValue();
                                        if (target == null) {
                                            player.sendMessage(thumbNail() + ChatColor.RED + "That player doesn't exist");
                                            return true;
                                        }
                                        if (plugin.managers.getBalance(player1) >= payment) {
                                            plugin.managers.withdrawPlayer(player1, payment);
                                            plugin.managers.depositPlayer(target, payment);
                                            player1.sendMessage(ChatColor.GREEN + target.getName() + " §7has §a$" + plugin.managers.getBalance(target) + "§7 in their account");
                                            target.sendMessage(thumbNail() + ChatColor.GREEN + player1.getName() + " §7has paid you  §a$" + payment);
                                        } else {
                                            player1.sendMessage(thumbNail() + ChatColor.RED + " You do not have the money to pay to the person" + target.getName());
                                        }
                                    } else {
                                        player.sendMessage(thumbNail() + ChatColor.RED + " You don't have permission to use this command".trim());
                                    }
                                } else {
                                    sendMessage(player, thumbNail() + "Usage: /eco pay <player> <money>");
                                }

                            } catch (NumberFormatException exception) {
                                //do nothing hehehe hacky way go brrr
                                player.sendMessage(ChatColor.RED + " Error: Too much money cannot transfer that".trim());
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("You have to be a player");
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("SimpleEconomy.admin.reload")) {
                        Data.reload();
                        Data.save();
                        plugin.reloadConfig();
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.RED + plugin.getName() + "reloaded.");
                    }
                }
                if (args[0].equalsIgnoreCase("disable")) {
                    if (player.hasPermission("SimpleEconomy.admin.disable")) {
                        plugin.onDisable();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                sendMessage(player , "&3Commands:");
                if(!player.hasPermission("SimpleEconomy.admin.help")){
                    sendMessage(player, "&3/eco pay");
                    sendMessage(player, "&3/eco balance");
                }else{
                    sendMessage(player , "&3/eco deposit <Player> <money>");
                    sendMessage(player , "&3/eco remove <Player> <money>");
                    sendMessage(player, "&3/eco disable");
                    sendMessage(player, "&3/eco reload");
                }

            }

            return true;
        }
        return true;
    }

    private Double getDouble(String args) {
        String number = args.replaceAll("[^0-9]", "");
        double amount = Double.parseDouble(number);
        if (args.substring(args.length() - 1).equalsIgnoreCase("k"))
            amount *= 1000.0D;
        return Double.valueOf(amount);
    }

    private String thumbNail() {
        String args = new String();
        try {
            args = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.thumbnail"));
        } catch (NullPointerException exception) {
            System.out.println("Something in the config is wrong in the plugin" + plugin.getName());
        }
        return args;
    }
    private void sendMessage(CommandSender player,String string){
       player.sendMessage(ChatColor.translateAlternateColorCodes('&' , string));
    }
    private void sendMessage(Player player,String string){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&' , string));
    }
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            final List<String> completions = new ArrayList<>();
            if(args.length == 1){
                completions.add("pay");
                completions.add("balance");
                if(sender.hasPermission("SimpleEconomy.admin.help")){
                    completions.add("deposit");
                    completions.add("remove");
                    completions.add("disable");
                    completions.add("reload");
                }
                return completions;
            }
            List<String> playerNames = new ArrayList<>();
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("pay")){
                    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                    Bukkit.getServer().getOnlinePlayers().toArray(players);
                    for (int i = 0; i < players.length; i++){
                        playerNames.add(players[i].getName());
                    }
                    return playerNames;
                }
                if(sender.hasPermission("NoEconomy.admin.help")){
                    if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("remove")) {
                        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                        Bukkit.getServer().getOnlinePlayers().toArray(players);
                        for (int i = 0; i < players.length; i++){
                            playerNames.add(players[i].getName());
                        }
                        return playerNames;
                    }
                }
            }
            if(args.length == 3){
                if(args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("remove")){
                    final List<String> completion = new ArrayList<>();
                    completion.add("1");
                    completion.add("10");
                    completion.add("100");
                    completion.add("1000");
                    completion.add("10000");
                    return completion;
                }
            }
            
          return null;
        }
}




