package net.ilexiconn.llibrary.command;

import java.awt.Desktop;
import java.util.ArrayList;
import java.util.List;

import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.color.EnumChatColor;
import net.ilexiconn.llibrary.update.ChangelogHandler;
import net.ilexiconn.llibrary.update.ModUpdateContainer;
import net.ilexiconn.llibrary.update.UpdateHelper;
import net.ilexiconn.llibrary.update.VersionHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Lists;

public class CommandLLibrary extends CommandBase
{
    public String getName()
    {
        return "llibrary";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/llibrary list OR /llibrary update <modid> OR /llibrary changelog <modid> <version>";
    }

    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    public void execute(ICommandSender sender, String[] args) throws WrongUsageException
    {
        String title = "[LLibHelper]" + EnumChatFormatting.YELLOW + " ";
        List<ModUpdateContainer> outdatedMods = VersionHandler.getOutdatedMods();

        if (args.length >= 1)
        {
            if (args[0].equalsIgnoreCase("list"))
            {
                if (args.length > 1)
                {
                    throw new WrongUsageException("/llibrary list");
                }

                ChatHelper.chatTo(sender, new ChatMessage("--- Showing a list of outdated mods ---", EnumChatColor.DARK_GREEN));

                for (ModUpdateContainer mod : outdatedMods)
                {
                    ChatHelper.chatTo(sender, new ChatMessage("(" + mod.modid + ") ", EnumChatColor.BLUE), new ChatMessage(mod.name + " version " + mod.version + " - Latest version: " + mod.latestVersion, EnumChatColor.WHITE));
                }

                ChatHelper.chatTo(sender, new ChatMessage("Use ", EnumChatColor.GREEN), new ChatMessage("/llibrary update <modid>", EnumChatColor.YELLOW), new ChatMessage(" to update the desired mod, ", EnumChatColor.GREEN), new ChatMessage("or", EnumChatColor.RED));
                ChatHelper.chatTo(sender, new ChatMessage("Use ", EnumChatColor.GREEN), new ChatMessage("/llibrary changelog <modid> <version>", EnumChatColor.YELLOW), new ChatMessage(" to see its version changelog.", EnumChatColor.GREEN));

                return;
            }

            if (args[0].equalsIgnoreCase("update"))
            {
                if (args.length != 2)
                {
                    throw new WrongUsageException("/llibrary update <modid>");
                }

                for (ModUpdateContainer mod : outdatedMods)
                {
                    if (args[1].equalsIgnoreCase(mod.modid))
                    {
                        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

                        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
                        {
                            try
                            {
                                desktop.browse(mod.website.toURI());
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                return;
            }

            if (args[0].equalsIgnoreCase("changelog"))
            {
                if (args.length != 3)
                {
                    throw new WrongUsageException("/llibrary changelog <modid> <version>");
                }

                for (int i = 0; i < UpdateHelper.modList.size(); ++i)
                {
                    ModUpdateContainer mod = UpdateHelper.modList.get(i);

                    if (args[1].equalsIgnoreCase(mod.modid))
                    {
                        boolean hasChangelogForVersion = false;

                        try
                        {
                            hasChangelogForVersion = ChangelogHandler.hasModGotChangelogForVersion(mod, args[2]);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        if (hasChangelogForVersion)
                        {
                            LLibrary.proxy.openChangelogGui(mod, args[2]);
                        }
                        else
                        {
                            ChatHelper.chatTo(sender, new ChatMessage("There is no changelog for mod '" + mod.modid + "' version " + args[2] + "!", EnumChatColor.RED));
                        }
                    }
                }

                return;
            }
        }
        throw new WrongUsageException(getCommandUsage(sender));
    }

    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length == 1)
        {
            return getListOfStringsMatchingLastWord(astring, "list", "update", "changelog");
        }
        else
        {
            if (astring[0].equalsIgnoreCase("update") && astring.length == 2)
            {
                return getListOfStringsMatchingLastWord(astring, (String[]) getAllModIDs(VersionHandler.getOutdatedMods()).toArray());
            }
            if (astring[0].equalsIgnoreCase("changelog") && astring.length == 2)
            {
                return getListOfStringsMatchingLastWord(astring, (String[]) getAllModIDs(UpdateHelper.modList).toArray());
            }
            if (astring[0].equalsIgnoreCase("changelog") && astring.length == 3)
            {
                return getListOfStringsMatchingLastWord(astring, (String[]) getAllModChangelogs(UpdateHelper.getModContainerById(astring[1])).toArray());
            }
        }
        return null;
    }

    protected List getAllModIDs(List list)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (Object aCollection : list)
        {
            ModUpdateContainer mod = (ModUpdateContainer) aCollection;
            arraylist.add(mod.modid);
        }

        return arraylist;
    }

    protected List getAllModChangelogs(ModUpdateContainer mod)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (String string : mod.updateFile)
        {
            String s = mod.modid + "Log|";

            if (string.startsWith(s))
            {
                String s1 = string.substring(s.length()).split(":")[0];
                arraylist.add(s1);
            }
        }

        return arraylist;
    }
}