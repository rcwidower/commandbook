// $Id$
/*
 * Copyright (C) 2010, 2011 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.commandbook;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;

public class TimeLockManager {
    
    private CommandBookPlugin plugin;
    private Map<String, Integer> tasks = new HashMap<String, Integer>();
    
    public TimeLockManager(CommandBookPlugin plugin) {
        this.plugin = plugin;
    }
    
    public synchronized void unlock(World world) {
        Integer id = tasks.get(world.getName());
        if (id != null) {
            plugin.getServer().getScheduler().cancelTask(id);
        }
    }
    
    public synchronized void lock(World world) {
        long time = world.getFullTime();
        unlock(world);
        int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
                plugin, new TimeLocker(world, time), 20, 20);
        tasks.put(world.getName(), id);
    }

}
