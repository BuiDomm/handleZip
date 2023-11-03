/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Menu;

/**
 *
 * @author ASUS
 */
public class App extends Menu<String> {

    public App() {
        super("====================== Zip / Unzip file Programming =========================", choices);

    }

    static String[] choices = {"Zip File", "Unzip File", "Exit"};
    FileZipper fz = new FileZipper();

    @Override
    public void execute(int n) {
        switch (n) {
            case 1: {
                fz.zipFile();
                break;
            }
            case 2: {
                fz.unzipFile();
                break;
            }
            case 3: {
                System.out.println("Exiting program. Goodbye!");
                System.exit(0);
            }
        }

    }
}
