package cz.upol.jj.martinbrablik.flashcards.commands;

import java.util.ArrayList;
import java.util.List;

import cz.upol.jj.martinbrablik.flashcards.IRegistrator;

public abstract class Command implements IRegistrator
{
	public static List<Command> registeredCommands = new ArrayList<Command>(); //Seznam pøíkazù
	
	private String name;
	private String[] mandatoryArgs;
	private String[] optionalArgs;
	
	public Command(String name, String[] mandatory_args, String[] optional_args)
	{
		//Konstruktor pøíkazu. Pøi vytvoøení je nový pøíkaz zapsán do seznamu pøíkazù metodou register().
		this.name = name;
		this.mandatoryArgs = mandatory_args;
		this.optionalArgs = optional_args;
		this.register();
	}
	
	public String getName()
	{
		return this.name;
	}
	public String[] getMandatoryArgs()
	{
		return this.mandatoryArgs;
	}
	public void setMandatoryArgs(String[] args)
	{
		this.mandatoryArgs = args;
	}
	public String[] getOptionalArgs()
	{
		return this.optionalArgs;
	}
	public void setOptionalArgs(String[] args)
	{
		this.optionalArgs = args;
	}
	public void register()
	{
		if(!Command.registeredCommands.contains(this))
			Command.registeredCommands.add(this);
	}
	public void unregister()
	{
		if(Command.registeredCommands.contains(this))
			Command.registeredCommands.remove(this);
	}
	public abstract boolean execute(); //Provede pøíkaaz pro aktuální hodnoty mandatoryArgs a optionalArgs.
}
