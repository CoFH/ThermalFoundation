/*
    Adds White Wool as a Fuel to the Gourmand Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:gourmand_fuel>.addFuel(name as string, ingredient as IIngredient, energy as int);

<recipetype:thermal:gourmand_fuel>.addFuel("custom_gourmand_fuel", <item:minecraft:white_wool>, 5000);

/*
    Removes a Cookie as a fuel in the Disenchantment Dynamo.
*/

// <recipetype:thermal:gourmand_fuel>.removeFuel(outputItem as IItemStack);

<recipetype:thermal:gourmand_fuel>.removeFuel(<item:minecraft:cookie>);
