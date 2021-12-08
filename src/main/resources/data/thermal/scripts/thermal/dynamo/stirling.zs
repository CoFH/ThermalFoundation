/*
    Adds White Wool as a Fuel to the Stirling Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:stirling_fuel>.addFuel(name as string, ingredient as IIngredient, energy as int);

<recipetype:thermal:stirling_fuel>.addFuel("custom_stirling_fuel", <item:minecraft:white_wool>, 5000);

/*
    Removes Blaze Powder as a fuel in the Stirling Dynamo.
*/

// <recipetype:thermal:stirling_fuel>.removeFuel(outputItem as IItemStack);

<recipetype:thermal:stirling_fuel>.removeFuel(<item:minecraft:blaze_powder>);
