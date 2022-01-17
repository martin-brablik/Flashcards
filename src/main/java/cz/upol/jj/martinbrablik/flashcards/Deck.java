package cz.upol.jj.martinbrablik.flashcards;

import java.util.ArrayList;
import java.util.List;

import cz.upol.jj.martinbrablik.flashcards.orders.IOrder;
import cz.upol.jj.martinbrablik.flashcards.orders.ScoreOrder;
import cz.upol.jj.martinbrablik.flashcards.orders.TimeOrder;

public class Deck implements IRegistrator
{
	public static List<Deck> registeredDecks = new ArrayList<Deck>(); //Seznam bal��k�
	public static IOrder[] availableOrderMethods = new IOrder[] {new TimeOrder(), new ScoreOrder()}; //Seznam objekt� spravuj�c�ch uspo��d�n� karet v bal��ku
	
	public static Deck findByName(String name)
	{
		//Ze seznamu bal��k� vr�t� bal��ek se zadan�m jm�nem. Pokud ho nenajde vr�t� null.
		for(int i = 0; i < registeredDecks.size(); i++)
		{
			if(registeredDecks.get(i).getName().equals(name))
				return registeredDecks.get(i);
		}
		return null;
	}
	
	private String name;
	private List<Card> cards = new ArrayList<Card>();
	public IOrder orderMethod;
	private int newLimit = 10;
	private int repeatedLimit = 10;
	
	public Deck(String name)
	{
		//Konstruktor bal��ku. P�i vytvo�en� je nov� bal��ek zaps�n do seznamu bal��k� metodou register.
		this.name = name;
		this.orderMethod = Deck.availableOrderMethods[1];
		this.register();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public void addCard(Card card)
	{
		this.cards.add(card);
	}
	public void removeCard(Card card)
	{
		this.cards.remove(card);
	}
	public void removeCardById(int card_id)
	{
		removeCard(findCard(card_id));
	}
	public Card findCard(int id)
	{
		////Ze seznamu karet v aktu�ln�m bal��ku vr�t� kartu se zadan�m ID. Pokud ji nenajde vr�t� null.
		for(int i = 0; i < this.getCards().size(); i++)
		{
			if(this.cards.get(i).getId() == id)
				return this.getCards().get(i);
		}
		System.out.println("Karta se v bal��ku " + this.getName() + " nenach�z�");
		return null;
	}
	public String getName()
	{
		return this.name;
	}
	public List<Card> getCards()
	{
		return this.cards;
	}
	public int getMaxId(List<Card> cards)
	{
		//Vr�t� nejvy��� ID karty ze seznamu karet 
		if(!cards.isEmpty())
		{
			int max = -1;
			for(Card card : cards)
			{
				if(card.getId() > max)
					max = card.getId();
			}
			return max;
		}
		return 0;
	}
	public int getNewLimit()
	{
		return this.newLimit;
	}
	public int getRepeatedLimit()
	{
		return this.repeatedLimit;
	}
	public void setNewLimit(int new_limit)
	{
		this.newLimit = new_limit;
	}
	public void setRepeatedLimit(int repeated_limit)
	{
		this.repeatedLimit = repeated_limit;
	}
	public void register()
	{
		if(!Deck.registeredDecks.contains(this))
			Deck.registeredDecks.add(this);
	}
	public void unregister()
	{
		if(Deck.registeredDecks.contains(this))
			Deck.registeredDecks.remove(this);
	}
}