/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.plantandnurserymanagementapp;
import com.mycompany.plantandnurserymanagementapp.AuthUsers.SignInForm;
import javax.swing.SwingUtilities;


/**
 *
 * @author Kapnang
 */

// The entry point of the application


public class PlantAndNurseryManagementApp {

    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new SignInForm().setVisible(true));
    }
}
