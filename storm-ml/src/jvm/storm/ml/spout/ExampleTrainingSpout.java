package storm.ml.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import storm.ml.spout.BaseTrainingSpout;

public class ExampleTrainingSpout extends BaseTrainingSpout {
    int samples_count = 0;
    int max_samples = 100;

    public static int get_label(Double x, Double y) {
        // arbitrary expected output (for testing purposes)
        return (2*x + 1 < y)? 1 : 0;
    }

    @Override
    public void nextTuple() {
        if (this.samples_count < this.max_samples) {
            Double x = 100 * Math.random();
            Double y = 100 * Math.random();

            List<Double> example = new ArrayList<Double>();
            example.add(x);
            example.add(y);

            int label = ExampleTrainingSpout.get_label(x, y);

            _collector.emit(new Values(example.toString(), label));

            this.samples_count++;
        }
    }
}
