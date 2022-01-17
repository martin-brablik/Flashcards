package cz.upol.jj.martinbrablik.flashcards.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class StudyCommand extends Command
{
	//P��kaz pro spu�t�n� zkou�en�
	private List<Integer> againCards = new ArrayList<Integer>(); //Seznam zapomenut�ch karet
	private Scanner input = new Scanner(System.in);
	private Deck d; //Zkou�en� bal��ek
	private int newCardsShown = 0; //Po�et ji� zkou�en�ch nov�ch karet
	private int repeatedCardsShown = 0; //Po�et ji� zjkou�en�ch opakuj�c�ch se karet
	
	public StudyCommand(String name, String[] mandatory_args, String[] optional_args) {
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		reset(); //Nastaven� v�choz�ch hodnot tohoto objektu
		try
		{
			d = Deck.findByName(this.getMandatoryArgs()[0]); //Z�sk�n� bal��ku ze seznamu bal��k�
			if(d == null)
			{
				System.out.println("Chyba: Bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			if(!d.getCards().isEmpty())
			{
				for(int i = 0; i < d.getCards().size(); i++) //Projde v�echny karty v bal��ku.
				{
					if(displayCard(i)) //Zobraz� p�edn� stranu karty pokud je to mo�n�.
						evaluateCard(i);
				}
			}
			for(int i = 0; i < againCards.size(); i++) //Projde v�echny karty kter� byly p�i vyhodnocen� za�azeny do seznamu againCards.
			{
				displayCardAgain(againCards.get(i)); //Zobraz� kartu znovu.
				evaluateAgain(againCards.get(i)); //Vyhodnot� kartu znovu.
			}
			System.out.println("\nTento bal�k m�te prozat�m hotov.");
			DataHandler.saveDeck(d); //Ulo�� bal��ek v�etn� jeho karet do soubor�.
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		
	}
	private boolean displayCard(int index)
	{
		//Zobraz� p�edn� stranu karty
		if(d.getCards().get(index).isNew)
		{
			if(newCardsShown < d.getNewLimit())
			{
				//Pokud nen� p�ekro�en limit nov�ch karet ke zkou�en�
				System.out.println("\n" + d.getCards().get(index).getSide(0));
				input.nextLine();
				System.out.println(d.getCards().get(index).getSide(1));
				newCardsShown++;
				System.out.println("1 - Znovu (0)");
				System.out.println("2 - Dobr� (+1)");
				System.out.println("3 - Snadn� (+2)");
				return true;
			}
			return false;
		}
		else
		{
			if(repeatedCardsShown < d.getRepeatedLimit())
			{
				//Pokud nen� p�ekro�en limit opakuj�c�ch se karet ke zkou�en�
				System.out.println("\n" + d.getCards().get(index).getSide(0));
				input.nextLine();
				System.out.println(d.getCards().get(index).getSide(1));
				repeatedCardsShown++;
				System.out.println("1 - Znovu (-2)");
				System.out.println("2 - T�k� (-1)");
				System.out.println("3 - Dobr� (+1)");
				System.out.println("4 - Snadn� (+2)");
				return true;
			}
			return false;
		}
	}
	private int evaluateCard(int index)
	{
		//Vyhodnot� kartu podle vstupu u�ivatele (uprav� sk�re karty a p��padn� ji p�id� do seznamu againCards).
		//Nov� karty se vyhodnocuj� jinak ne� opakuj�c� se karty.
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
		//Zobraz� p�edn� stranu karty ze seznamu againCards.
		System.out.println("\n" + d.getCards().get(index).getSide(0));
		input.nextLine();
		System.out.println(d.getCards().get(index).getSide(1));
		newCardsShown++;
		System.out.println("1 - T�k� (0)");
		System.out.println("2 - Dobr� (+1)");
	}
	private void evaluateAgain(int index)
	{
		//Vyhodnot� kartu ze seznamu aginCards.
		//Karty ze seznamu againCards jsou vyhodnocov�ny jinak ne� ostatn� karty.
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
		//Nastav� v�choz� hodnoty tohoto objektu.
		this.againCards = new ArrayList<Integer>();
		this.d = null;
		this.newCardsShown = 0;
		this.repeatedCardsShown = 0;
	}
}