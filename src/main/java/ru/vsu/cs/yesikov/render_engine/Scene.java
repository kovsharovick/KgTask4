package ru.vsu.cs.yesikov.render_engine;

import ru.vsu.cs.yesikov.model.Model;

import java.util.LinkedList;
import java.util.List;


public class Scene {

	private final List<Model> models;
	private List<Model> activeModels;
	private final List<String> modelNames;
	private final List<CameraController> cameraControllers;
	private CameraController currentCameraController;
	private final List<String> cameraNames;

	public Scene() {
		cameraNames = new LinkedList<>();
		cameraControllers = new LinkedList<>();
		models = new LinkedList<>();
		activeModels = new LinkedList<>();
		modelNames = new LinkedList<>();
	}

	public void deleteCamera(int index, String name) {
		getCameraNames().remove(name);
		getCameraControllers().remove(index);
	}

	public void deleteSelectedModels(List<Integer> indexesToDelete, List<String> namesToDelete) {
		int decrement = 0;
		for (int index : indexesToDelete) {
			getModels().remove(index - decrement++);
		}

		getActiveModels().removeIf(model -> ! getModels().contains(model));
		getModelNames().removeIf(namesToDelete::contains);
	}

	public List<Model> getModels() {
		return models;
	}

	public List<Model> getActiveModels() {
		return activeModels;
	}

	public void setActiveModels(List<Model> activeModels) {
		this.activeModels = activeModels;
	}

	public List<String> getModelNames() {
		return modelNames;
	}

	public List<CameraController> getCameraControllers() {
		return cameraControllers;
	}

	public CameraController getCurrentCameraController() {
		return currentCameraController;
	}

	public void setCurrentCameraController(CameraController currentCameraController) {
		this.currentCameraController = currentCameraController;
	}

	public List<String> getCameraNames() {
		return cameraNames;
	}
}
