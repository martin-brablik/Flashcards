package cz.upol.jj.martinbrablik.flashcards.orders;

import java.util.Collections;
import java.util.Comparator;

import cz.upol.jj.martinbrablik.flashcards.Card;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class ScoreOrder implements IOrder
{
	public boolean reverse = false;
	private final String name = "Podle skóre";
	private final String unlocalized_name = "score";
	
	@Override
	public void order(Deck d)
	{
		//Seøadí karty v balíèku podle jejich skóre.
		d.getCards().sort(Comparator.comparing(Card::getScore));
		if(this.reverse)
			Collections.reverse(d.getCards());
	}
	@Override
	public boolean getReverse()
	{
		return this.reverse;
	}
	@Override
	public void setReverse(boolean value)
	{
		this.reverse = value;
	}
	@Override
	public String getName()
	{
		return this.name;
	}
	@Override
	public String getUnlocalizedName()
	{
		return this.unlocalized_name;
	}
}
