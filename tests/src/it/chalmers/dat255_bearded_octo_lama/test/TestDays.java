/**
 * Copyright (C) 2012 Emil Edholm, Emil Johansson, Johan Andersson, Johan Gustafsson
 *
 * This file is part of dat255-bearded-octo-lama
 *
 *  dat255-bearded-octo-lama is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  dat255-bearded-octo-lama is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with dat255-bearded-octo-lama.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.chalmers.dat255_bearded_octo_lama.test;

import it.chalmers.dat255_bearded_octo_lama.utilities.Days;
import it.chalmers.dat255_bearded_octo_lama.utilities.Weekday;

import java.util.Calendar;
import java.util.EnumSet;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test for the {@link it.chalmers.dat255_bearded_octo_lama.utilities.Days} class.
 * @author Emil Edholm
 * @date 20 okt 2012
 */
public class TestDays extends TestCase {

	private Days noDays;
	private Days allDays;
	private Days weekdays, weekend;
	
	@Override
	public final void setUp() {
		noDays   = new Days();
		allDays  = new Days(EnumSet.allOf(Weekday.class));
		
		weekdays = new Days(Weekday.MONDAY, Weekday.FRIDAY);
		weekend  = new Days(Weekday.SATURDAY, Weekday.SUNDAY); 
	}
	
	/**
	 * Test method for {@link it.chalmers.dat255_bearded_octo_lama.utilities.Days#decode(int)} 
	 * and
	 * {@link it.chalmers.dat255_bearded_octo_lama.utilities.Days#encode()}.
	 */
	public final void testEncodeDecode() {
		// Assert correct encoding/decoding if no days have been added.
		Days decodedValue = Days.decode(noDays.encode());
		Assert.assertTrue(noDays.equals(decodedValue));
		
		// Assert correct encoding/decoding if all days were added.
		decodedValue = Days.decode(allDays.encode());
		Assert.assertTrue(allDays.equals(decodedValue));
		
		// Assert correct encoding/decoding if only a subset of days were added.
		decodedValue = Days.decode(weekdays.encode());
		Assert.assertTrue(weekdays.equals(decodedValue));
		
		// More of the same.
		decodedValue = Days.decode(weekend.encode());
		Assert.assertTrue(weekend.equals(decodedValue));
	}

	public final void testInitDaysConstructor() {
		Days d = new Days(Weekday.WEDNESDAY);
		
		Assert.assertEquals(1, d.size());
		Assert.assertTrue(d.contains(Weekday.WEDNESDAY));
	}
	
	public final void testStartEndConstructor() {
		int daysInWeekend = 2;
		Assert.assertEquals(daysInWeekend, weekend.size());
		
		Assert.assertTrue(weekend.contains(Weekday.SATURDAY));
		Assert.assertTrue(weekend.contains(Weekday.SUNDAY));
	}
	
	public final void testBaseUponConstructor() {
		EnumSet<Weekday> base = EnumSet.of(Weekday.TUESDAY);
		Days d = new Days(base);
		
		Assert.assertEquals(base.size(), d.size());
		Assert.assertTrue(d.contains(Weekday.TUESDAY));
	}
	
	public final void testEquals() {
		Days d1 = new Days(Weekday.MONDAY);
		Days d2 = new Days(Weekday.TUESDAY);
		d1.add(Weekday.TUESDAY);
		d2.add(Weekday.MONDAY);
		
		Assert.assertTrue(d1.equals(d2));
		
		d2.add(Weekday.WEDNESDAY);
		
		Assert.assertFalse(d1.equals(d2));
	}
	
	public final void testDaysLeft() {
		Calendar cal = Calendar.getInstance();
		Days d = new Days(Weekday.WEDNESDAY);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Assert.assertEquals(2, d.daysLeft(cal));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		Assert.assertEquals(0, d.daysLeft(cal));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		Assert.assertEquals(6, d.daysLeft(cal));
		
		d = new Days();
		Assert.assertTrue(d.isEmpty());
		Assert.assertEquals(-1, d.daysLeft());
	}
}
