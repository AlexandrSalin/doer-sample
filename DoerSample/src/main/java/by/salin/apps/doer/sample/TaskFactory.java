/*
 * Copyright 2013 Alexandr Salin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package by.salin.apps.doer.sample;


import by.salin.apps.doer.utils.tasks.ITask;
import by.salin.apps.doer.utils.tasks.ITaskFactory;
import by.salin.apps.doer.utils.tasks.impl.ComplexTask;
import by.salin.apps.doer.utils.tasks.impl.StubTask;

/**
 * Created by Alexandr.Salin on 22.12.13.
 */
public class TaskFactory implements ITaskFactory
{
	@Override
	public ITask stubTask(Object id)
	{
		return new StubTask(id, 1000);
	}

	@Override
	public ITask stubComplexTask(Object id)
	{
		ITask firstTask = new StubTask(id + "__1", 100);
		firstTask.setProgressPart(0.5f);
		ITask secondTask = new StubTask(id + "__2", 500);
		secondTask.setProgressPart(0.25f);
		ITask thirdTask = new StubTask(id + "__3", 200);
		thirdTask.setProgressPart(0.25f);
		ITask[] array = new ITask[]{firstTask, secondTask, thirdTask};
		return new ComplexTask(id, array);
	}
}
