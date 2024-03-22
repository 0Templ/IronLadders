package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.world.level.block.WeatheringCopper;

public class CopperLadderBlock extends BaseMetalLadder implements WeatheringCopper {

    private final WeatherState weatherState;

    public CopperLadderBlock(WeatherState weatherState, Properties settings) {
        super(settings, LadderType.COPPER);
        this.weatherState = weatherState;
    }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.copperLadderSpeedMultiplier;
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }

}
