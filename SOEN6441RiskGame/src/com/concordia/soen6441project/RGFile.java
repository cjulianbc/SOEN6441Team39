package com.concordia.soen6441project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class RGFile {
	private File file;
	private JFileChooser fileChooser=new JFileChooser();
	
	void openFile()
	{
		if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{	
			file=fileChooser.getSelectedFile();
		}
	}
	
	void saveFile(String continents, String countries) throws IOException
	{
		if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
		{	
			file=fileChooser.getSelectedFile();
			FileWriter fileWriter = new FileWriter(file);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print("[Continents]\n");
		    printWriter.print(continents);
		    printWriter.print("[Territories]\n");
		    printWriter.print(countries);
		    printWriter.close();
		}
		
	}

	StringBuilder getContent(String label) throws FileNotFoundException
	{
		StringBuilder content=new StringBuilder();
		Scanner buffer=new Scanner(file);
		String actualLine;
		String endChar;
		while(buffer.hasNext())
		{
			actualLine=buffer.nextLine();
			if(actualLine.equals(label))
			{
				while(buffer.hasNext())
				{
					actualLine=buffer.nextLine();
					if(!actualLine.equals(""))
					{
						endChar=actualLine.substring(0, 1);
						if(endChar.equals("["))
							break;
						else
						{
							content.append(actualLine);
							content.append("\n");
						}
					}
					else
					{
						content.append("\n");
					}
				}
				break;
			}
		}
		buffer.close();
		return content;
	}
	
}
