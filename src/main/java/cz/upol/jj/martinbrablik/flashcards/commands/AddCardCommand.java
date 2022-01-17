package cz.upol.jj.martinbrablik.flashcards.commands;

import java.util.Arrays;

import cz.upol.jj.martinbrablik.flashcards.Card;
import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class AddCardCommand extends Command
{
	//Pøíkaz pro pøidání karty do balíèku
	public AddCardCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			if(Deck.findByName(this.getMandatoryArgs()[0]) == null)
			{
				//Pokud se nepodaøí v seznamu balíèkù najít balíèek se zadaným jménem pøíkaz skonèí cybou.
				System.out.println("Chyba: Kartu se nepodaøilo pøidat, protože balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			Card c = new Card(Deck.findByName(this.getMandatoryArgs()[0]), Arrays.copyOfRange(this.getMandatoryArgs(), 1, this.getMandatoryArgs().length)); //Vytvoøení objektu karty a pøidání do balíèku.
			Deck.findByName(this.getMandatoryArgs()[0]).orderMethod.order(Deck.findByName(this.getMandatoryArgs()[0])); //Setøízení karet v balíèku
			System.out.println("Karta byla pøidána do balíèku " + this.getMandatoryArgs()[0]);
			DataHandler.saveCard(c); //Uložení souboru karty
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chbya: Nesprávný poèet argumentù");
			return false;
		}
	}
}