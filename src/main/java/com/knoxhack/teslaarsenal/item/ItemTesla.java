package com.knoxhack.teslaarsenal.item;


import com.knoxhack.teslaarsenal.BaseTeslaArsenalTeslaContainerProvider;
import com.knoxhack.teslaarsenal.TeslaArsenalTeslaUtilities;

import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import java.util.List;
import java.util.Set;
public class ItemTesla extends ItemBase
{
	private long maxCapacity;
	private long output;
	private long input;

	public ItemTesla(float f, float g, ToolMaterial diamond, Set<Block> effectiveOn, long maxCapacity, long input, long output)
	{
		setMaxStackSize(1);
		this.maxCapacity = maxCapacity;
		this.output = output;
		this.input = input;
	}

	@Override public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		ItemStack powered = TeslaArsenalTeslaUtilities.createChargedStack(new ItemStack(itemIn));
		ItemStack unpowered = new ItemStack(itemIn);
		subItems.add(powered);
		subItems.add(unpowered);
	}

	@Override public boolean isRepairable()
	{
		return false;
	}

	@Override public double getDurabilityForDisplay(ItemStack stack)
	{
		return (1 - (double) TeslaArsenalTeslaUtilities.getStoredPower(stack) / (double) TeslaArsenalTeslaUtilities.getMaxCapacity(stack));
}

	@Override public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@Override public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(I18n.format(TextFormatting.DARK_AQUA + "" + TeslaArsenalTeslaUtilities.getStoredPower(stack) + "/" + TeslaArsenalTeslaUtilities
				.getMaxCapacity(stack) + " Tesla"));
	}

	@Override public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
		return new BaseTeslaArsenalTeslaContainerProvider(new BaseTeslaContainer(), maxCapacity, output, input);
	}
}