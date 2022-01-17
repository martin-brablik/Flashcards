package cz.upol.jj.martinbrablik.flashcards;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import cz.upol.jj.martinbrablik.flashcards.commands.Command;
import cz.upol.jj.martinbrablik.flashcards.commands.SetOrderCommand;

public class DataHandler
{
	public static boolean saveCard(Card c)
	{
		//Vytvo�� soubor ze zedan� karty.
		try
		{
			if(Files.notExists(Paths.get("data/" + c.getDeck().getName() + "_cards")))
				Files.createDirectory(Paths.get("data/" + c.getDeck().getName() + "_cards")); //Vytvo�it slo�ku data a vn� slo�ku s n�zvem bal��ku pokud neexistuj�.
			List<String> data_to_write = new ArrayList<String>(); //Seznam ��dk� kter� se zap�� do souboru.
			//Vytvo�en� jednotliv�ch ��dk�
			data_to_write.add("side0=" + c.getSide(0));
			data_to_write.add("side1=" + c.getSide(1));
			data_to_write.add("score=" + c.getScore());
			data_to_write.add("isNew=" + Boolean.toString(c.isNew));
			Files.write(Paths.get("data/" + c.getDeck().getName() + "_cards/" + Integer.toString(c.getId())), data_to_write, StandardCharsets.UTF_8); //Zaps�n� dat do souboru.
		}
		catch(Exception e)
		{
			System.out.println("Chyba: Nepoda�ilo se uol�it soubor karty");
			return false;
		}
		return true;
	}
	public static boolean saveDeck(Deck d)
	{
		//Vytvo�� soubor ze zadan�ho bal��ku.
		try
		{
			if(Files.notExists(Paths.get("data")))
				Files.createDirectory(Paths.get("data")); //Vytvo�it slo�ku data pokud neexistuje.
			List<String> data_to_write = new ArrayList<String>(); //Seznam ��dk� kter� se zap�� do souboru.
			//Vytvo�en� jednotliv�ch ��dk�
			data_to_write.add("orderMethod=" + d.orderMethod.getUnlocalizedName());
			data_to_write.add("newLimit=" + d.getNewLimit());
			data_to_write.add("repeatedLimit=" + d.getRepeatedLimit());
			data_to_write.add("reverseOrder=" + Boolean.toString(d.orderMethod.getReverse()));
			if(Files.notExists(Paths.get("data/" + d.getName() + "_cards")))
				Files.createDirectory(Paths.get("data/" + d.getName() + "_cards"));
			Files.write(Paths.get("data/" + d.getName()), data_to_write, StandardCharsets.UTF_8, StandardOpenOption.CREATE); //Zaps�n� dat do souboru.
			return true;
		}
		catch(IOException e)
		{
			System.out.println("Chyba: Nepoda�ilo se uol�it soubor bal��ku");
			e.printStackTrace();
			return false;
		}
	}
	public static boolean loadFromFile(String file_name, boolean is_card)
	{
		//Vytvo�� objekt bal��ku ze souboru.
		try
		{
			if(Files.exists(Paths.get("data/" + file_name)))
			{
				try
				{
					List<String> deck_data = Files.readAllLines(Paths.get("data/" + file_name)); //Seznam ��dk� ze souboru
					Deck d = new Deck(file_name); //Vytvo�� nov� bal��ek se jm�nem souboru.
					//Z�sk�n� a nastaven� hodnot z jednotliv�ch ��dk� nov� vytvo�en�mu bal��u.
					d.setNewLimit(Integer.parseInt(deck_data.get(1).split("=")[1]));
					d.setRepeatedLimit(Integer.parseInt(deck_data.get(2).split("=")[1]));
					d.orderMethod.setReverse(Boolean.parseBoolean(deck_data.get(3).split("=")[1]));
					if(Files.exists(Paths.get("data/" + file_name + "_cards")))
					{
						//Na�ten� karet bal��ku
						File[] card_files = new File("data/" + file_name + "_cards").listFiles(); //Seznam soubor� karet
						for(int i = 0; i < card_files.length; i++)
						{
							if(card_files[i].isFile())
							{
								List<String> lines = Files.readAllLines(card_files[i].toPath()); //Seznam ��dk� ze souboru karty.
								Card c = new Card(d, new String[] {lines.get(0).split("=")[1], lines.get(1).split("=")[1]}); //Vytvo�� novou kartu a nastav� jej� strany podle hodnot z�skan�ch ze souboru.
								//Z�sk�n� a nastaven� ostatn�ch hodnot karty
								c.setScore(Integer.parseInt(lines.get(2).split("=")[1]));
								c.setId(Integer.parseInt(card_files[i].getName()));
								c.isNew = Boolean.parseBoolean(lines.get(3).split("=")[1]);
							}
						}
					}
					SetOrderCommand setOrder = (SetOrderCommand)Command.registeredCommands.get(7); //Nastaven� po�ad� karet spu�t�n�m p��kazu.
					setOrder.setMandatoryArgs(new String[] {file_name, deck_data.get(0).split("=")[1]}); //Nastaven� argument� p��kazu.
					setOrder.execute(); //Spu�t�n� p��kazu
					return true;
				}
				catch(NumberFormatException e)
				{
					System.out.println("Chyba: Soubor nem� spr�vn� form�t");
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Chyba: Nepoda�ilo se z�skat p��stup k souboru");
		}
		return true;
	}
	public static void loadDecks()
	{
		//Na�te v�echny soubory bal��k� a vytvo�� z nich objekty pomoc� funkce loadFromFile.
		try
		{
			if(Files.notExists(Paths.get("data")))
				Files.createDirectory(Paths.get("data")); //Vytvo�� slo�ku data pokud neexistuje.
			File dataFolder = new File("data");
			for(File deckFile : dataFolder.listFiles())
			{
				if(deckFile.isFile())
				{
					loadFromFile(deckFile.getName(), false); //Pro ka�d� soubor ve slo�ce data provede funkci loadFromFile().
				}
			}
		}
		catch(NullPointerException|IOException e)
		{
			System.out.println("Chyba: P�i na��t�n� n�kter�ch soubor� do�lo k chyb�");
		}
	}
	public static void SaveDecks()
	{
		//Ulo�� bal��ek a v�echny jeho karty do soubor�
		for(Deck d : Deck.registeredDecks)
		{
			saveDeck(d);
			for(Card c : d.getCards())
				saveCard(c);
		}
	}
	public static void deleteCard(Card c)
	{
		//Odstran� soubor karty.
		try
		{
			if(!Files.deleteIfExists(Paths.get("data/" + c.getDeck().getName() + "_cards/" + Integer.toString(c.getId()))))
				System.out.println("Chyba: Soubor karty se nepoda�ilo odstranit");
		}
		catch(NullPointerException|IOException e)
		{
			System.out.println("Chyba: Soubor karty se nepoda�ilo odstranit");
		}
	}
	public static void deleteDeck(Deck d)
	{
		//Odstran� soubor bal��ku a soubory jeho karet.
		try
		{
			Files.deleteIfExists(Paths.get("data/" + d.getName()));
			File[] card_files = new File("data/" + d.getName() + "_cards").listFiles();
			if(card_files != null)
			{
				for(int i = 0; i < card_files.length; i++)
				{
					if(Files.exists(card_files[i].toPath()))
					{
						if(card_files[i].isFile())
						{
							Files.deleteIfExists(card_files[i].toPath());
						}
					}
				}
			}
			Files.deleteIfExists(Paths.get("data/" + d.getName() + "_cards"));
		}
		catch(NullPointerException|IOException e)
		{
			System.out.println("Chyba: Soubor bal��ku se nepoda�ilo odstranit");
			e.printStackTrace();
			return;
		}
	}
}