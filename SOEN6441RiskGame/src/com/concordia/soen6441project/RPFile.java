package com.concordia.soen6441project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class RPFile {
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
	
	void createGraph(StringBuilder content)
	{
		Scanner buffer = new Scanner(content.toString());
		String searchChar=",";
		int index1=0;
		int index2;
		int i,j;
		String[][] vertex = new String[100][7];
		String edge;
		RGGraph graph=new RGGraph();
		i=0;
		while (buffer.hasNextLine())
		{
			String actualLine = buffer.nextLine();
			j=0;
			while(!actualLine.equals("")) 
			{
				 index2=actualLine.indexOf(searchChar);
				 if (index2!=-1) 
				 { 
					if (j<4)
					{
						vertex[i][j]=actualLine.substring(index1, index1+index2);
						if(j==0)
							graph.addVertex(vertex[i][j]);
						actualLine=","+actualLine;
						actualLine=actualLine.replace(","+vertex[i][j]+",", "");
						j++;
					}
					else
					{
						edge=actualLine.substring(index1, index1+index2);
						graph.addEdge(vertex[i][0], edge);
						actualLine=","+actualLine;
						actualLine=actualLine.replace(","+edge+",", "");
					}
				 }
				 else
				 {
					 graph.addEdge(vertex[i][0], actualLine);
					 break;
				 }
			}
			if(!actualLine.equals(""))
			{
				i++;
				ArrayList<String> edgeList = graph.getEdges(vertex[i-1][0]);
				System.out.print(vertex[i-1][0]+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
			}
			
		}
		buffer.close();
	}

}
