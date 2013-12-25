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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.ProgressBar;
import by.salin.apps.doer.broker.TaskBroker;
import by.salin.apps.doer.utils.events.AddNewTaskEvent;
import by.salin.apps.doer.utils.events.TaskProgressUpdateEvent;
import by.salin.apps.jems.EventHandlerCallback;
import by.salin.apps.jems.JEMS;
import by.salin.apps.jems.impl.Event;

/**
 * @author Alexandr.Salin
 */
public class DoerSampleActivity extends ActionBarActivity implements EventHandlerCallback
{
	private static final String TAG = DoerSampleActivity.class.getSimpleName();
	private static final String ID_TASK = "task";
	private static final String ID_COMPLEX_TASK = "complex_task";
	private Button btn_single;
	private Button btn_complex;
	private ProgressBar bar_single;
	private ProgressBar bar_complex;
	private TaskFactory taskFactory = new TaskFactory();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_doer_sample);
		btn_single = (Button) findViewById(R.id.task_id_btn);
		btn_single.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				JEMS.dispatcher().sendEvent(new AddNewTaskEvent(taskFactory.stubTask(ID_TASK)));
			}
		});
		btn_complex = (Button) findViewById(R.id.task_complex_id_btn);
		btn_complex.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				JEMS.dispatcher().sendEvent(new AddNewTaskEvent(taskFactory.stubComplexTask(ID_COMPLEX_TASK)));
			}
		});
		bar_single = (ProgressBar) findViewById(R.id.task_id);
		bar_complex = (ProgressBar) findViewById(R.id.task_complex_id);
		startWorker();
	}

	private void startWorker()
	{
		TaskBroker.getInstance();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		JEMS.dispatcher().addListenerOnEvent(TaskProgressUpdateEvent.class, this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		JEMS.dispatcher().removeListenerOnEvent(TaskProgressUpdateEvent.class, this);
	}

	@Override
	public void onEvent(Event event)
	{
		String id = (String) ((TaskProgressUpdateEvent) event).getId();
		float progress = ((TaskProgressUpdateEvent) event).getProgress();
		if (ID_TASK.equals(id))
		{
			bar_single.setProgress((int) (progress * 100));
		}
		else
		{
			bar_complex.setProgress((int) (progress * 100));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doer_sample, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId())
		{
			case R.id.action_settings:
				//TODO
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.frg_doer_sample, container, false);

			return rootView;
		}
	}

}