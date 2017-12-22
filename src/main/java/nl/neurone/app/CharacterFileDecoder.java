package nl.neurone.app;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import nl.neurone.component.CharacterDecoder;
import nl.neurone.stream.BitInputStreamFile;

public class CharacterFileDecoder {
	private CharacterDecoder decoder;
	private BitInputStreamFile bitInputStream;

	public static void main(String args[]) {
		String inputFileName = args[0];
		String outputFileName = args[1];

		CharacterFileDecoder fileEncoder = new CharacterFileDecoder();
		fileEncoder.createBitInputStream(inputFileName);
		fileEncoder.decodeFile(outputFileName);
	}

	private void createBitInputStream(String inputFileName) {
		bitInputStream = new BitInputStreamFile(inputFileName);
		decoder = new CharacterDecoder(bitInputStream);
	}

	private void decodeFile(String fileName) {
        Instant start = Instant.now();
		System.out.println("Start: " + new Date());
		long actualSize = bitInputStream.readLong();

		try (BufferedWriter os = new BufferedWriter(new FileWriter(fileName))) {
			long decodedSize = 0;
			while (decodedSize < actualSize) {
				char c = (char) decoder.decodeValue(bitInputStream);
				os.write(c);
//				System.out.print(c);
				decodedSize++;
			}
            System.out.println("Size actual: " + actualSize);
            Instant end = Instant.now();
			System.out.println("Stopped: " + new Date());
            System.out.println("Took seconds: " + Duration.between(start, end));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}