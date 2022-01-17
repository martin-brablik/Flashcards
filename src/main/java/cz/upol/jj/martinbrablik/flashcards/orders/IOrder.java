package cz.upol.jj.martinbrablik.flashcards.orders;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public interface IOrder
{
	//Spr�va �azen� karet v bal��ku
	public void order(Deck d); //Metoda se�ad� karty v bal��ku.
	public void setReverse(boolean value); //Metoda nastav� hodnotu reverse. Pokud je reverse true budou se karty �adit v opa�n�m po�ad�.
	public boolean getReverse(); //Metoda vr�t� hodnotu reverse.
	public String getName(); //Metoda vr�t� popis �azen�.
	public String getUnlocalizedName(); //Metoda vr�t� n�zev �azen� pou��van� programem.
}