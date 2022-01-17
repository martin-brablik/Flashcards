package cz.upol.jj.martinbrablik.flashcards.orders;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public interface IOrder
{
	//Správa øazení karet v balíèku
	public void order(Deck d); //Metoda seøadí karty v balíèku.
	public void setReverse(boolean value); //Metoda nastaví hodnotu reverse. Pokud je reverse true budou se karty øadit v opaèném poøadí.
	public boolean getReverse(); //Metoda vrátí hodnotu reverse.
	public String getName(); //Metoda vrátí popis øazení.
	public String getUnlocalizedName(); //Metoda vrátí název øazení používaný programem.
}