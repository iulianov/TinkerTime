package aohara.tinkertime.views.menus;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import aohara.tinkertime.ModManager;

public class MenuFactory {
	
	public static JToolBar createToolBar(ModManager mm){
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		toolBar.add(new Actions.LaunchKspAction(toolBar, mm)).setFocusPainted(false);
		toolBar.add(new Actions.OpenGameDataFolder(toolBar, mm)).setFocusPainted(false);		
		toolBar.add(new Actions.OptionsAction(toolBar, mm)).setFocusPainted(false);
		
		toolBar.addSeparator();
		
		toolBar.add(new Actions.AddModAction(toolBar, mm)).setFocusPainted(false);
		toolBar.add(new Actions.AddModZip(toolBar, mm)).setFocusPainted(false);

		toolBar.addSeparator();
		
		toolBar.add(new Actions.UpdateModAction(toolBar, mm)).setFocusPainted(false);
		toolBar.add(new Actions.CheckforUpdatesAction(toolBar, mm)).setFocusPainted(false);
		
		return toolBar;
	}
	
	public static JMenuBar createMenuBar(ModManager mm){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu modMenu = new JMenu("Mod");
		modMenu.add(new Actions.EnableDisableModAction(menuBar, mm).withoutIcon());
		modMenu.add(new Actions.DeleteModAction(menuBar, mm).withoutIcon());
		modMenu.add(new Actions.UpdateModAction(menuBar, mm).withoutIcon());
		menuBar.add(modMenu);
		
		JMenu updateMenu = new JMenu("Updates");
		updateMenu.add(new Actions.UpdateAllAction(menuBar, mm).withoutIcon());
		updateMenu.add(new Actions.CheckforUpdatesAction(menuBar, mm).withoutIcon());
		updateMenu.add(new Actions.UpdateTinkerTime(menuBar, mm).withoutIcon());
		menuBar.add(updateMenu);
		
		JMenu importExportMenu = new JMenu("Import/Export");
		importExportMenu.add(new Actions.ExportMods(menuBar, mm).withoutIcon());
		importExportMenu.add(new Actions.ImportMods(menuBar, mm).withoutIcon());
		menuBar.add(importExportMenu);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new Actions.AboutAction(menuBar, mm).withoutIcon());
		helpMenu.add(Actions.newHelpAction(menuBar).withoutIcon());
		helpMenu.add(new Actions.ContactAction(menuBar, mm).withoutIcon());
		menuBar.add(helpMenu);
		
		return menuBar;
	}
	
	public static JPopupMenu createPopupMenu(ModManager mm){
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new Actions.EnableDisableModAction(popupMenu, mm));
		popupMenu.add(new Actions.UpdateModAction(popupMenu, mm));
		popupMenu.add(new Actions.DeleteModAction(popupMenu, mm));
		return popupMenu;
	}

}
