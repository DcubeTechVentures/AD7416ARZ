// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// AD7416ARZ
// This code is designed to work with the AD7416ARZ_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Analog-Digital-Converters?sku=AD7416ARZ_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class AD7416ARZ
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus Bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, AD7416ARZ I2C address is 0x48(72)
		I2CDevice device = Bus.getDevice(0x48);

		// Read 2 bytes of data from address 0x00(00)
		// temp msb, temp lsb
		byte[] data = new byte[2];
		device.read(0x00, data, 0, 2);
		
		// Convert the data to 10-bits
		int temp = (((data[0] & 0xFF) * 256) + (data[1] & 0xC0)) / 64;
		if(temp > 511)
		{
			temp -= 1024;
		}
		double cTemp = temp * 0.25;
		double fTemp = cTemp * 1.8 + 32;
		
		// Output data to screen
		System.out.printf("Temperature in Celsius : %.2f C %n", cTemp);
		System.out.printf("Temperature in Farhenheit : %.2f F %n", fTemp); 
	}
}