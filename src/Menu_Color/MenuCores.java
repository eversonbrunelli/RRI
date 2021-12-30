/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu_Color;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 *
 * @author eversonbrunelli-fit
 */
public class MenuCores {
    
    private void customizeMenuBar(JMenuBar menuBar) {

    menuBar.setUI(new BasicMenuBarUI() {

        @Override
        public void paint(Graphics g, JComponent c) {
            g.setColor(Color.black);
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }

    });

    MenuElement[] menus = menuBar.getSubElements();

    for (MenuElement menuElement : menus) {

        JMenu menu = (JMenu) menuElement.getComponent();
        changeComponentColors(menu);
        menu.setOpaque(true);

        MenuElement[] menuElements = menu.getSubElements();

        for (MenuElement popupMenuElement : menuElements) {

            JPopupMenu popupMenu = (JPopupMenu) popupMenuElement.getComponent();
            popupMenu.setBorder(null);

            MenuElement[] menuItens = popupMenuElement.getSubElements();

            for (MenuElement menuItemElement : menuItens) {

                JMenuItem menuItem = (JMenuItem) menuItemElement.getComponent();
                changeComponentColors(menuItem);
                menuItem.setOpaque(true);

            }
        }
    }
}

private void changeComponentColors(Component comp) {
    comp.setBackground(Color.black);
    comp.setForeground(Color.white);
}
    
}
