package cz.upol.jj.martinbrablik.flashcards;

public class Card
{		
	private int id; //Unik�tn� identifik�tor karty, slou�� k identifikaci karty v bal��ku a je pou�ito pro �azen� karet podle �asu p�id�n�.
	private Deck deck;
	private String[] sides;
	private int score;
	public boolean isNew = true;;
	
	public Card(Deck deck, String[] sides)
	{
		//Konstruktor bal��ku. P�i vytvo�en� se karta vlo�� do zadan�ho bal��ku
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
