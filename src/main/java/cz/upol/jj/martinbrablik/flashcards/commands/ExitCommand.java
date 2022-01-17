package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;

public class ExitCommand extends Command
{
	//Pøíkaz pro ukonèení programu.
	public ExitCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		DataHandler.SaveDecks();
		System.exit(0);
		return true;
	}
}