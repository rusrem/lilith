/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2013 Joern Huxhorn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.huxhorn.lilith.swing.actions;

import de.huxhorn.sulky.conditions.Condition;
import de.huxhorn.sulky.conditions.Not;

public class ExcludeHttpStatusCodeAction
		extends FocusHttpStatusCodeAction
{
	private static final long serialVersionUID = 634934854671373620L;

	@Override
	protected Condition resolveCondition()
	{
		Condition condition = super.resolveCondition();
		if(condition == null)
		{
			return null;
		}
		return new Not(condition);
	}
}