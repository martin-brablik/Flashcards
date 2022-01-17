package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public class BrowseDecksCommand extends Command
{
	//P��kaz vyp�e v�echny informace o v�ech bal��c�ch
	public BrowseDecksCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		//Po�et karet v bal��ku se zobraz� jako X / Y kde X je men�� nebo rovno limitu karet ke zkou�en� a Y je rovno celkov�mu po�tu karet v bal��ku
		for(int i = 0; i < Deck.registeredDecks.size(); i++)
		{
			System.out.println("\n" + Deck.registeredDecks.get(i).getName());
			int new_cards_available = 0;
			int new_cards_total = 0;
			int repeated_cards_available = 0;
			int repeated_cards_total = 0;
			for(int j = 0; j < Deck.registeredDecks.get(i).getCards().size(); j++)
			{
				if(Deck.registeredDecks.get(i).getCards().get(j).isNew)
				{
					new_cards_total++;
					if(j < Deck.registeredDecks.get(i).getNewLimit())
						new_cards_available++;
				}
			}
			for(int j = 0; j < Deck.registeredDecks.get(i).getCards().size(); j++)
			{
				if(!Deck.registeredDecks.get(i).getCards().get(j).isNew)
				{
					repeated_cards_total++;
					if(j < Deck.registeredDecks.get(i).getRepeatedLimit())
						repeated_cards_available++;
				}
			}
			System.out.println("Po�et nov�ch karet: " + Integer.toString(new_cards_available) + " / " + Integer.toString(new_cards_total));
			System.out.println("Po�et karet k opakov�n�: " + Integer.toString(repeated_cards_available) + " / " + Integer.toString(repeated_cards_total));
			System.out.println("Po�ad� karet: " + Deck.registeredDecks.get(i).orderMethod.getName());
		}
		return true;
	}
}

