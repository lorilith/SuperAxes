package com.github.levoment.superaxes;

import com.github.levoment.superaxes.Items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.Properties;


public class SuperAxesMod implements ModInitializer {

	public static String MODID = "lvmnt";
	public static final ItemGroup SUPERAXES_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "superaxes"), () -> new ItemStack(ModItems.WoodenSuperAxe));
	// Config file
	public static File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "superaxes.properties");
	// Config properties
	public static boolean harvestLeaves = false;

	@Override
	public void onInitialize() {

		// Load configuration
		loadConfig(this.configFile);

		// Initialize items
		ModItems.initializeItems();

		// Populate the list of the mod's items and their identifiers
        ModItems.poplateMapOfIdentifiers();

		// Register the items
		ModItems.registerItems();
	}

	public static void loadConfig(File configFile) {
		try {
			// If the config file doesn't exist, create it
			if (configFile.createNewFile()) {
				// Create the properties
				Properties configProperties = new Properties();
				// Set the properties
				configProperties.setProperty("harvestLeaves", String.valueOf(false));
				// Save the properties to the config file
				saveConfig(configFile, configProperties);
				// Load the saved configuration
				loadConfig(configFile);
			} else {
				// File exists, let's load the current properties
				InputStream input = new FileInputStream(configFile);
				// Create a Properties object for reading the properties
				Properties readProperties = new Properties();
				// Read the properties
				readProperties.load(input);
				// Set the property
				SuperAxesMod.harvestLeaves = Boolean.parseBoolean(readProperties.getProperty("harvestLeaves"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig(File configFile, Properties properties) {
		try {
			// Create the output stream
			OutputStream output = new FileOutputStream(configFile);
			// Save the properties to the file
			properties.store(output, null);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
