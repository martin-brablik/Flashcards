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
		//Vytvoøí soubor ze zedané karty.
		try
		{
			if(Files.notExists(Paths.get("data/" + c.getDeck().getName() + "_cards")))
				Files.createDirectory(Paths.get("data/" + c.getDeck().getName() + "_cards")); //Vytvoøit složku data a vní složku s názvem balíèku pokud neexistují.
			List<String> data_to_write = new ArrayList<String>(); //Seznam øádkù které se zapíší do souboru.
			//Vytvoøení jednotlivých øádkù
			data_to_write.add("side0=" + c.getSide(0));
			data_to_write.add("side1=" + c.getSide(1));
			data_to_write.add("score=" + c.getScore());
			data_to_write.add("isNew=" + Boolean.toString(c.isNew));
			Files.write(Paths.get("data/" + c.getDeck().getName() + "_cards/" + Integer.toString(c.getId())), data_to_write, StandardCharsets.UTF_8); //Zapsání dat do souboru.
		}
		catch(Exception e)
		{
			System.out.println("Chyba: Nepodaøilo se uolžit soubor karty");
			return false;
		}
		return true;
	}
	public static boolean saveDeck(Deck d)
	{
		//Vytvoøí soubor ze zadaného balíèku.
		try
		{
			if(Files.notExists(Paths.get("data")))
				Files.createDirectory(Paths.get("data")); //Vytvoøit složku data pokud neexistuje.
			List<String> data_to_write = new ArrayList<String>(); //Seznam øádkù které se zapíší do souboru.
			//Vytvoøení jednotlivých øádkù
			data_to_write.add("orderMethod=" + d.orderMethod.getUnlocalizedName());
			data_to_write.add("newLimit=" + d.getNewLimit());
			data_to_write.add("repeatedLimit=" + d.getRepeatedLimit());
			data_to_write.add("reverseOrder=" + Boolean.toString(d.orderMethod.getReverse()));
			if(Files.notExists(Paths.get("data/" + d.getName() + "_cards")))
				Files.createDirectory(Paths.get("data/" + d.getName() + "_cards"));
			Files.write(Paths.get("data/" + d.getName()), data_to_write, StandardCharsets.UTF_8, StandardOpenOption.CREATE); //Zapsání dat do souboru.
			return true;
		}
		catch(IOException e)
		{
			System.out.println("Chyba: Nepodaøilo se uolžit soubor balíèku");
			e.printStackTrace();
			return false;
		}
	}
	public static boolean loadFromFile(String file_name, boolean is_card)
	{
		//Vytvoøí objekt balíèku ze souboru.
		try
		{
			if(Files.exists(Paths.get("data/" + file_name)))
			{
				try
				{
					List<String> deck_data = Files.readAllLines(Paths.get("data/" + file_name)); //Seznam øádkù ze souboru
					Deck d = new Deck(file_name); //Vytvoøí nový balíèek se jménem souboru.
					//Získání a nastavení hodnot z jednotlivých øádkù novì vytvoøenému balíèu.
					d.setNewLimit(Integer.parseInt(deck_data.get(1).split("=")[1]));
					d.setRepeatedLimit(Integer.parseInt(deck_data.get(2).split("=")[1]));
					d.orderMethod.setReverse(Boolean.parseBoolean(deck_data.get(3).split("=")[1]));
					if(Files.exists(Paths.get("data/" + file_name + "_cards")))
					{
						//Naètení karet balíèku
						File[] card_files = new File("data/" + file_name + "_cards").listFiles(); //Seznam souborù karet
						for(int i = 0; i < card_files.length; i++)
						{
							if(card_files[i].isFile())
							{
								List<String> lines = Files.readAllLines(card_files[i].toPath()); //Seznam øádkù ze souboru karty.
								Card c = new Card(d, new String[] {lines.get(0).split("=")[1], lines.get(1).split("=")[1]}); //Vytvoøí novou kartu a nastaví její strany podle hodnot získaných ze souboru.
								//Získání a nastavení ostatních hodnot karty
								c.setScore(Integer.parseInt(lines.get(2).split("=")[1]));
								c.setId(Integer.parseInt(card_files[i].getName()));
								c.isNew = Boolean.parseBoolean(lines.get(3).split("=")[1]);
							}
						}
					}
					SetOrderCommand setOrder = (SetOrderCommand)Command.registeredCommands.get(7); //Nastavení poøadí karet spuštìním pøíkazu.
					setOrder.setMandatoryArgs(new String[] {file_name, deck_data.get(0).split("=")[1]}); //Nastavení argumentù pøíkazu.
					setOrder.execute(); //Spuštìní pøíkazu
					return true;
				}
				catch(NumberFormatException e)
				{
					System.out.println("Chyba: Soubor nemá správný formát");
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Chyba: Nepodaøilo se získat pøístup k souboru");
		}
		return true;
	}
	public static void loadDecks()
	{
		//Naète všechny soubory balíèkù a vytvoøí z nich objekty pomocí funkce loadFromFile.
		try
		{
			if(Files.notExists(Paths.get("data")))
				Files.createDirectory(Paths.get("data")); //Vytvoøí složku data pokud neexistuje.
			File dataFolder = new File("data");
			for(File deckFile : dataFolder.listFiles())
			{
				if(deckFile.isFile())
				{
					loadFromFile(deckFile.getName(), false); //Pro každý soubor ve složce data provede funkci loadFromFile().
				}
			}
		}
		catch(NullPointerException|IOException e)
		{
			System.out.println("Chyba: Pøi naèítání nìkterých souborù došlo k chybì");
		}
	}
	public static void SaveDecks()
	{
		//Uloží balíèek a všechny jeho karty do souborù
		for(Deck d : Deck.registeredDecks)
		{
			saveDeck(d);
			for(Card c : d.getCards())
				saveCard(c);
		}
	}
	public static void deleteCard(Card c)
	{
		//Odstraní soubor karty.
		try
		{
			if(!Files.deleteIfExists(Paths.get("data/" + c.getDeck().getName() + "_cards/" + Integer.toString(c.getId()))))
				System.out.println("Chyba: Soubor karty se nepodaøilo odstranit");
		}
		catch(NullPointerException|IOException e)
		{
			System.out.println("Chyba: Soubor karty se nepodaøilo odstranit");
		}
	}
	public static void deleteDeck(Deck d)
	{
		//Odstraní soubor balíèku a soubory jeho karet.
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
			System.out.println("Chyba: Soubor balíèku se nepodaøilo odstranit");
			e.printStackTrace();
			return;
		}
	}
}