package cz.upol.jj.martinbrablik.flashcards;

public class Card
{		
	private int id; //Unikátní identifikátor karty, slouí k identifikaci karty v balíèku a je pouito pro øazení karet podle èasu pøidání.
	private Deck deck;
	private String[] sides;
	private int score;
	public boolean isNew = true;;
	
	public Card(Deck deck, String[] sides)
	{
		//Konstruktor balíèku. Pøi vytvoøení se karta vloí do zadaného balíèku
		this.deck = deck;
		this.sides = sides;
		deck.addCard(this);
		this.id = this.deck.getMaxId(this.deck.getCards()) + 1;
	}
	
	public int getScore()
	{
		return this.score;
	}
	public void setScore(int score)
	{
		if(score >= 0)
			this.score = score;
	}
	
	public String getSide(int index)
	{
		if(index >= 1)
			return this.sides[1];
		return this.sides[0];
	}
	public void setSide(int side_index, String content)
	{
		if(side_index < 2)
			this.sides[side_index] = content;
	}
	public Deck getDeck()
	{
		return this.deck;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
}
