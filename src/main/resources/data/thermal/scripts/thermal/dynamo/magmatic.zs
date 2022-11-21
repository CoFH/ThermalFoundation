/*
    Adds Latex as a Fuel to the Magmatic Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:magmatic_fuel>.addFuel(name as string, ingredient as CTFluidIngredient, energy as int);

<recipetype:thermal:magmatic_fuel>.addFuel("custom_magmatic_fuel", <fluid:thermal:latex>, 5000);

/*
    Removes Lava a fuel for the Magmatic Dynamo.
*/

// <recipetype:thermal:magmatic_fuel>.removeFuel(outputFluid as IFluidStack);

<recipetype:thermal:magmatic_fuel>.removeFuel(<fluid:minecraft:lava>);
