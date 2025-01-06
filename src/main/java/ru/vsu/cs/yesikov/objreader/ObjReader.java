package ru.vsu.cs.yesikov.objreader;

import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.math.Vector3f;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.model.Polygon;

import java.util.*;

public class ObjReader {

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public static Model read(String fileContent) {
		Model result = new Model();
		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);

		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine().trim();
			lineInd++;

			if (line.isEmpty() || line.startsWith("g")) { // Игнорируем пустые строки и комментарии
				continue;
			}

			ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
			final String token = wordsInLine.remove(0);

			try {
				switch (token) {
					case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
					case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
					case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
					case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd));
					default -> throw new ObjReaderException("Unknown token: " + token, lineInd);
				}
			} catch (ObjReaderException e) {
				System.err.println("Error in line " + lineInd + ": " + e.getMessage());
			} catch (Exception e) {
				System.err.println("Unexpected error in line " + lineInd + ": " + e.getMessage());
			}
		}

		validateModel(result, lineInd); // Проверяем целостность модели после парсинга
		return result;
	}

	protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			checkArgumentCount(wordsInLineWithoutToken, 3, lineInd, "vertex");
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);
		}
	}

	protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			checkArgumentCount(wordsInLineWithoutToken, 2, lineInd, "texture vertex");
			return new Vector2f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)));
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);
		}
	}

	protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			checkArgumentCount(wordsInLineWithoutToken, 3, lineInd, "normal");
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);
		}
	}

	protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		Polygon result = new Polygon();
		ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
		ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
		ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

		for (String s : wordsInLineWithoutToken) {
			parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
		}

		result.setVertexIndices(onePolygonVertexIndices);
		result.setTextureVertexIndices(onePolygonTextureVertexIndices);
		result.setNormalIndices(onePolygonNormalIndices);
		return result;
	}

	protected static void parseFaceWord(
			String wordInLine,
			ArrayList<Integer> onePolygonVertexIndices,
			ArrayList<Integer> onePolygonTextureVertexIndices,
			ArrayList<Integer> onePolygonNormalIndices,
			int lineInd) {
		String[] wordIndices = wordInLine.split("/");
		try {
			switch (wordIndices.length) {
				case 1 -> {
					onePolygonVertexIndices.add(parseIndex(wordIndices[0], lineInd));
				}
				case 2 -> {
					onePolygonVertexIndices.add(parseIndex(wordIndices[0], lineInd));
					onePolygonTextureVertexIndices.add(parseIndex(wordIndices[1], lineInd));
				}
				case 3 -> {
					onePolygonVertexIndices.add(parseIndex(wordIndices[0], lineInd));
					// Если текстура присутствует
					if (!wordIndices[1].isEmpty()) {
						onePolygonTextureVertexIndices.add(parseIndex(wordIndices[1], lineInd));
					}
					onePolygonNormalIndices.add(parseIndex(wordIndices[2], lineInd));
				}
				default -> throw new ObjReaderException("Invalid face element size.", lineInd);
			}
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse index value.", lineInd);
		}
	}

	private static int parseIndex(String indexStr, int lineInd) {
		int index = Integer.parseInt(indexStr) - 1; // Понижаем индекс для соответствия нулевому индексу
		if (index < 0) {
			throw new ObjReaderException("Index cannot be negative.", lineInd);
		}
		return index;
	}

	private static void checkArgumentCount(ArrayList<String> arguments, int expectedCount, int lineInd, String type) {
		if (arguments.size() > expectedCount) {
			throw new ObjReaderException("Too few arguments for " + type + " at line " + lineInd, lineInd);
		}
	}

	private static void validateModel(Model model, int totalLines) {
		if (model.vertices.isEmpty()) {
			System.err.println("Warning: No vertices found in the model. Check the input file.");
		}
		if (model.polygons.isEmpty()) {
			System.err.println("Warning: No polygons found in the model. Check the input file.");
		}

		// Проверка на соответствие индексов
		for (Polygon polygon : model.polygons) {
			for (int index : polygon.getVertexIndices()) {
				if (index >= model.vertices.size()) {
					throw new ObjReaderException("Vertex index out of bounds: " + index + " at line " + totalLines, index);
				}
			}
			for (int index : polygon.getTextureVertexIndices()) {
				if (index >= model.textureVertices.size()) {
					throw new ObjReaderException("Texture vertex index out of bounds: " + index + " at line " + totalLines, index);
				}
			}
			for (int index : polygon.getNormalIndices()) {
				if (index >= model.normals.size()) {
					throw new ObjReaderException("Normal index out of bounds: " + index + " at line " + totalLines, index);
				}
			}
		}
	}
}