package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class AddDeckCommand extends Command
{
	//Pøíkaz pro vytvoøení balíèku
	public AddDeckCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		Deck d = null;
		try
		{
			d = new Deck(this.getMandatoryArgs()[0]); //Vytvoøení objektu balíèku se zadaným jménem a pøidání balíèku do seznamu balíèkù.
			//Kontrola nepovinných argumentù. Pokud má argument výchozí hodnotu, znamená to, že nebyl uživatelem specifikován.
			if(!this.getOptionalArgs()[0].equals("[order]"))
			{
				//Pžíkaz s indexem 7 ze seznamu pøíkazù je pøíkaz pro nastavení øazení karet v balíèku.
				Command.registeredCommands.get(7).setMandatoryArgs(new String[] {d.getName(), this.getOptionalArgs()[0]}); //Nastavení argumentù pøíkazu pro seøazení.
				if(!Command.registeredCommands.get(7).execute()) //Spuštìní pøíkazu pro seøazení.
				{
					d.unregister();
					return false;
				}
			}
			try
			{
				if(!this.getOptionalArgs()[1].equals("[newCardsLimit]"))
					d.setNewLimit(Integer.parseInt(this.getOptionalArgs()[1])); //Nastavení limitu nových karet ke zkoušení.
			}
			catch (NumberFormatException e)
			{
				System.out.println("Varování: Nesprávnì zadaný argument " + this.getOptionalArgs()[1]);
				System.out.println("Bude použita výchozí hodnota");
			}
			try
			{
				if(!this.getOptionalArgs()[2].equals("[repeatedCardsLimit]"))
					d.setRepeatedLimit(Integer.parseInt(this.getOptionalArgs()[2])); //Nastavení limitu opakujících se karet ke zkoušení.
			}
			catch (NumberFormatException e)
			{
				System.out.println("Varování: Nesprávnì zadaný argument " + this.getOptionalArgs()[2]);
				System.out.println("Bude použita výchozí hodnota");
			}
			System.out.println("Balíèek " + this.getMandatoryArgs()[0] + " byl úspìšnì vytvoøen");
			DataHandler.saveDeck(d); //Uložení souboru balíèku.
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chbya: Nesprávný poèet argumentù");
			d.unregister(); //Pokud došlo pøi nastavování hodnot balíèku k chybì, je balíèek odstranìn ze seznamu balíèkù.
			return false;
		}
	}
}
