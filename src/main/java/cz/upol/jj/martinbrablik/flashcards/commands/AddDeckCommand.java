package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class AddDeckCommand extends Command
{
	//P��kaz pro vytvo�en� bal��ku
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
			d = new Deck(this.getMandatoryArgs()[0]); //Vytvo�en� objektu bal��ku se zadan�m jm�nem a p�id�n� bal��ku do seznamu bal��k�.
			//Kontrola nepovinn�ch argument�. Pokud m� argument v�choz� hodnotu, znamen� to, �e nebyl u�ivatelem specifikov�n.
			if(!this.getOptionalArgs()[0].equals("[order]"))
			{
				//P��kaz s indexem 7 ze seznamu p��kaz� je p��kaz pro nastaven� �azen� karet v bal��ku.
				Command.registeredCommands.get(7).setMandatoryArgs(new String[] {d.getName(), this.getOptionalArgs()[0]}); //Nastaven� argument� p��kazu pro se�azen�.
				if(!Command.registeredCommands.get(7).execute()) //Spu�t�n� p��kazu pro se�azen�.
				{
					d.unregister();
					return false;
				}
			}
			try
			{
				if(!this.getOptionalArgs()[1].equals("[newCardsLimit]"))
					d.setNewLimit(Integer.parseInt(this.getOptionalArgs()[1])); //Nastaven� limitu nov�ch karet ke zkou�en�.
			}
			catch (NumberFormatException e)
			{
				System.out.println("Varov�n�: Nespr�vn� zadan� argument " + this.getOptionalArgs()[1]);
				System.out.println("Bude pou�ita v�choz� hodnota");
			}
			try
			{
				if(!this.getOptionalArgs()[2].equals("[repeatedCardsLimit]"))
					d.setRepeatedLimit(Integer.parseInt(this.getOptionalArgs()[2])); //Nastaven� limitu opakuj�c�ch se karet ke zkou�en�.
			}
			catch (NumberFormatException e)
			{
				System.out.println("Varov�n�: Nespr�vn� zadan� argument " + this.getOptionalArgs()[2]);
				System.out.println("Bude pou�ita v�choz� hodnota");
			}
			System.out.println("Bal��ek " + this.getMandatoryArgs()[0] + " byl �sp�n� vytvo�en");
			DataHandler.saveDeck(d); //Ulo�en� souboru bal��ku.
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chbya: Nespr�vn� po�et argument�");
			d.unregister(); //Pokud do�lo p�i nastavov�n� hodnot bal��ku k chyb�, je bal��ek odstran�n ze seznamu bal��k�.
			return false;
		}
	}
}
