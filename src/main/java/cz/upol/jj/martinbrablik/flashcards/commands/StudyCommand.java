package cz.upol.jj.martinbrablik.flashcards.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class StudyCommand extends Command
{
	//Pøíkaz pro spuštìní zkoušení
	private List<Integer> againCards = new ArrayList<Integer>(); //Seznam zapomenutých karet
	private Scanner input = new Scanner(System.in);
	private Deck d; //Zkoušený balíèek
	private int newCardsShown = 0; //Poèet již zkoušených nových karet
	private int repeatedCardsShown = 0; //Poèet již zjkoušených opakujících se karet
	
	public StudyCommand(String name, String[] mandatory_args, String[] optional_args) {
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		reset(); //Nastavení výchozích hodnot tohoto objektu
		try
		{
			d = Deck.findByName(this.getMandatoryArgs()[0]); //Získání balíèku ze seznamu balíèkù
			if(d == null)
			{
				System.out.println("Chyba: Balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			if(!d.getCards().isEmpty())
			{
				for(int i = 0; i < d.getCards().size(); i++) //Projde všechny karty v balíèku.
				{
					if(displayCard(i)) //Zobrazí pøední stranu karty pokud je to možné.
						evaluateCard(i);
				}
			}
			for(int i = 0; i < againCards.size(); i++) //Projde všechny karty které byly pøi vyhodnocení zaøazeny do seznamu againCards.
			{
				displayCardAgain(againCards.get(i)); //Zobrazí kartu znovu.
				evaluateAgain(againCards.get(i)); //Vyhodnotí kartu znovu.
			}
			System.out.println("\nTento balík máte prozatím hotov.");
			DataHandler.saveDeck(d); //Uloží balíèek vèetnì jeho karet do souborù.
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		
	}
	private boolean displayCard(int index)
	{
		//Zobrazí pøední stranu karty
		if(d.getCards().get(index).isNew)
		{
			if(newCardsShown < d.getNewLimit())
			{
				//Pokud není pøekroèen limit nových karet ke zkoušení
				System.out.println("\n" + d.getCards().get(index).getSide(0));
				input.nextLine();
				System.out.println(d.getCards().get(index).getSide(1));
				newCardsShown++;
				System.out.println("1 - Znovu (0)");
				System.out.println("2 - Dobré (+1)");
				System.out.println("3 - Snadné (+2)");
				return true;
			}
			return false;
		}
		else
		{
			if(repeatedCardsShown < d.getRepeatedLimit())
			{
				//Pokud není pøekroèen limit opakujících se karet ke zkoušení
				System.out.println("\n" + d.getCards().get(index).getSide(0));
				input.nextLine();
				System.out.println(d.getCards().get(index).getSide(1));
				repeatedCardsShown++;
				System.out.println("1 - Znovu (-2)");
				System.out.println("2 - Tìžké (-1)");
				System.out.println("3 - Dobré (+1)");
				System.out.println("4 - Snadné (+2)");
				return true;
			}
			return false;
		}
	}
	private int evaluateCard(int index)
	{
		//Vyhodnotí kartu podle vstupu uživatele (upraví skóre karty a pøípadnì ji pøidá do seznamu againCards).
		//Nové karty se vyhodnocují jinak než opakující se karty.
		int option = 1;
		try
		{
			option = Integer.parseInt(input.nextLine());
		}
		catch(NumberFormatException e)
		{
			option = 1;
		}
		
		switch(option)
		{
		case 1:
			againCards.add(index);
			if(!d.getCards().get(index).isNew)
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() - 2);
			break;
		case 2:
			if(d.getCards().get(index).isNew)
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 1);
			else
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() - 1);
			break;
		case 3:
			if(d.getCards().get(index).isNew)
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 2);
			else
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 1);
			break;
		case 4:
			if(!d.getCards().get(index).isNew)
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 2);
			break;
		default:
			if(d.getCards().get(index).isNew)
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 2);
			else
				d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 1);
			break;
		}
		d.getCards().get(index).isNew = false;
		DataHandler.saveCard(d.getCards().get(index));
		return option;
	}
	private void displayCardAgain(int index)
	{
		//Zobrazí pøední stranu karty ze seznamu againCards.
		System.out.println("\n" + d.getCards().get(index).getSide(0));
		input.nextLine();
		System.out.println(d.getCards().get(index).getSide(1));
		newCardsShown++;
		System.out.println("1 - Tìžké (0)");
		System.out.println("2 - Dobré (+1)");
	}
	private void evaluateAgain(int index)
	{
		//Vyhodnotí kartu ze seznamu aginCards.
		//Karty ze seznamu againCards jsou vyhodnocovány jinak než ostatní karty.
		int option;
		try
		{
			option = Integer.parseInt(input.nextLine());
		}
		catch(NumberFormatException e)
		{
			option = 1;
		}
		if(option > 1)
		{
			d.getCards().get(index).setScore(d.getCards().get(index).getScore() + 1);
		}
		DataHandler.saveCard(d.getCards().get(index));
	}
	private void reset()
	{
		//Nastaví výchozí hodnoty tohoto objektu.
		this.againCards = new ArrayList<Integer>();
		this.d = null;
		this.newCardsShown = 0;
		this.repeatedCardsShown = 0;
	}
}