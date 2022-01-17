package cz.upol.jj.martinbrablik.flashcards;

import java.util.Arrays;
import java.util.Scanner;

import cz.upol.jj.martinbrablik.flashcards.commands.AddCardCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.AddDeckCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.BrowseCardsCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.BrowseDecksCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.Command;
import cz.upol.jj.martinbrablik.flashcards.commands.ExitCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.RemoveCardCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.RemoveDeckCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.RenameDeckCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.SetLimitCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.SetOrderCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.SetSideCommand;
import cz.upol.jj.martinbrablik.flashcards.commands.StudyCommand;

public class Main
{
	private static Command getInput()
	{
		//Funkce se pokus� sestavit p��kaz ze vstupu u�ivatele, vr�t� null pokud se to nepovede.
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String user_input = input.nextLine();
		String[] bits = user_input.split(" "); //Rozd�l� vstup do pole podle slov.
		String[] command_args;
		for(int i = 0; i < Command.registeredCommands.size(); i++) //Pokus� se nal�zt v seznamu p��kaz� p��kaz se zadan�m jm�nem (bits[0]).
		{
			if(bits[0].equalsIgnoreCase(Command.registeredCommands.get(i).getName()))
			{
				//P��kaz byl nalezen.
				//Odd�len� jm�na od argument�
				command_args = Arrays.copyOfRange(bits, 1, bits.length); //Argumenty (bits[1...])
				if(command_args.length >= Command.registeredCommands.get(i).getMandatoryArgs().length)
				{
					//Po�et zadan�ch argument� se shoduje s po�tem argument� p��kazu
					//Odd�len� povinn�ch a nepovinn�ch argument�
					int optional_args_count = command_args.length - Command.registeredCommands.get(i).getMandatoryArgs().length;
					Command.registeredCommands.get(i).setMandatoryArgs(Arrays.copyOfRange(command_args, 0, Command.registeredCommands.get(i).getMandatoryArgs().length));
					if(optional_args_count > 0)
						Command.registeredCommands.get(i).setOptionalArgs(Arrays.copyOfRange(command_args, Command.registeredCommands.get(i).getMandatoryArgs().length, command_args.length));
					return Command.registeredCommands.get(i); //Vr�t� sestaven� objekt p��kazu.
				}
				System.out.println("Chyba: Nespr�vn� po�et argument�");
				return null;
			}
		}
		System.out.println("Chyba: P��kaz " + bits[0] + " neexistuje");
		return null;
	}
	
	public static void main(String[] args)
	{
		//Registrace p��kaz�
		ExitCommand exit = new ExitCommand("exit", new String[]{}, new String[] {});
		AddDeckCommand addDeck = new AddDeckCommand("addDeck", new String[] {"<name>"}, new String[] {"[order]", "[newCardsLimit]", "[repeatedCardsLimit]"});
		AddCardCommand addCard = new AddCardCommand("addCard", new String[] {"<deck>", "<front>", "<back>"}, new String[] {});
		RemoveDeckCommand removeDeck = new RemoveDeckCommand("removeDeck", new String[] {"<name>"}, new String[] {});
		RemoveCardCommand removeCard = new RemoveCardCommand("removeCard", new String[] {"<deckName>", "<id>"}, new String[] {});
		BrowseDecksCommand browseDecks = new BrowseDecksCommand("browseDecks", new String[] {}, new String[] {});
		BrowseCardsCommand browseCards = new BrowseCardsCommand("browseCards", new String[] {"<deckName>"}, new String[] {});
		SetOrderCommand setOrder = new SetOrderCommand("setOrder", new String[] {"<deckName>", "<orderMethod>"}, new String[] {});
		SetLimitCommand setLimit = new SetLimitCommand("setLimit", new String[] {"<deckName>","<cardType>", "<limit>"}, new String[] {});
		StudyCommand study = new StudyCommand("study", new String[] {"<deckName>"}, new String[] {});
		RenameDeckCommand renameDeck = new RenameDeckCommand("renameDeck", new String[] {"<oldName>", "newName"}, new String[] {});
		SetSideCommand setSide = new SetSideCommand("setSide", new String[] {"<deckName>", "<cardID>", "<sideIndex>", "<sideContents>"}, new String[] {});
		
		DataHandler.loadDecks(); //Na�ten� dat ze soubor�
		
		System.out.println("[KMI / JJ] - Semstr�ln� Projekt\nAutor: Martin Brabl�k\n");
		
		while(true)
		{
			System.out.print(">");
			Command cmd = getInput();
			try
			{
				cmd.execute();
			}
			catch(NullPointerException e)
			{
				System.out.println("Chyba: Nespr�vn� zadan� p��kaz");
			}
		}
	}
}