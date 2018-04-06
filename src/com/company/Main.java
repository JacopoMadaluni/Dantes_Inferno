package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
	private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
		Game game = new Game();
		game.setTextSpeed("0");

		System.out.println("Do you want to load the last game? (y/n)\n>");
		if (loadSave()){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(new File("savings.txt")));
				String toLoad = reader.readLine();
				if (toLoad != null) {
					game.load(toLoad);
				}
			}catch(IOException e){
				System.out.println(e);
			}
			game.play(false);
		}else{
			game.play(true);
		}


    }

    public static boolean loadSave(){
		while(scanner.hasNext()){
			String input = scanner.next();
			if (input.toLowerCase().equals("y")){
				return true;
			}else if (input.toLowerCase().equals("n")){
				return false;
			}
		}
		return false;
	}

	public void testRes(){
    	try {
			File file = new File("savings.txt");
			System.out.println(file.getPath());
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("test787979");
			writer.close();

			//Files.write(Paths.get("savings.txt"), "test1".getBytes(), StandardOpenOption.WRITE);
			//System.out.println("file written");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
