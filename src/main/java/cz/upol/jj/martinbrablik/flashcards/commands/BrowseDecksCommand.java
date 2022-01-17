package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public class BrowseDecksCommand extends Command
{
	//Pøíkaz vypíše všechny informace o všech balíècích
	public BrowseDecksCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		//Poèet karet v balíèku se zobrazí jako X / Y kde X je menší nebo rovno limitu karet ke zkoušení a Y je rovno celkovému poètu karet v balíèku
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
			System.out.println("Poèet nových karet: " + Integer.toString(new_cards_available) + " / " + Integer.toString(new_cards_total));
			System.out.println("Poèet karet k opakování: " + Integer.toString(repeated_cards_available) + " / " + Integer.toString(repeated_cards_total));
			System.out.println("Poøadí karet: " + Deck.registeredDecks.get(i).orderMethod.getName());
		}
		return true;
	}
}

