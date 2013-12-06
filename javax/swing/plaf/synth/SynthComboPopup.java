/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.swing.plaf.synth;

import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * Synth's ComboPopup.
 *
 * @version %I%, %G%
 * @author Scott Violet
 */
class SynthComboPopup extends BasicComboPopup {
    
    public SynthComboPopup( JComboBox combo ) {
        super(combo);
    }

    /**
     * Configures the list which is used to hold the combo box items in the
     * popup. This method is called when the UI class
     * is created.
     *
     * @see #createList
     */
    @Override
    protected void configureList() {
        list.setFont( comboBox.getFont() );
        list.setCellRenderer( comboBox.getRenderer() );
        list.setFocusable( false );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        int selectedIndex = comboBox.getSelectedIndex();
        if ( selectedIndex == -1 ) {
            list.clearSelection();
        }
        else {
            list.setSelectedIndex( selectedIndex );
	    list.ensureIndexIsVisible( selectedIndex );
        }
        installListListeners();
    }
    
    /**
     * @inheritDoc
     * 
     * Overridden to take into account any popup insets specified in
     * SynthComboBoxUI
     */
    @Override
    protected Rectangle computePopupBounds(int px,int py,int pw,int ph) {
        ComboBoxUI ui = comboBox.getUI();
        if (ui instanceof SynthComboBoxUI) {
            SynthComboBoxUI sui = (SynthComboBoxUI)ui;
            if (sui.popupInsets != null) {
                Insets i = sui.popupInsets;
                return super.computePopupBounds(
                        px + i.left,
                        py + i.top,
                        pw - i.left - i.right,
                        ph - i.top - i.bottom);
            }
        }
        return super.computePopupBounds(px, py, pw, ph);
    }
}
