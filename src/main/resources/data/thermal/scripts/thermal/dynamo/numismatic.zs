/*
    Adds White Wool as a Fuel to the Numismatic Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:numismatic_fuel>.addFuel(name as string, ingredient as IIngredient, energy as int);

<recipetype:thermal:numismatic_fuel>.addFuel("custom_numismatic_fuel", <item:minecraft:white_wool>, 5000);

/*
    Removes Tin Coins as a fuel in the Numismatic Dynamo.
*/

// <recipetype:thermal:numismatic_fuel>.removeFuel(outputItem as IItemStack);

<recipetype:thermal:numismatic_fuel>.removeFuel(<item:thermal:tin_coin>);
