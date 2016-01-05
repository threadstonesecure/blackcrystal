import React from 'react';

import {PieChart} from 'react-d3-components';
import d3 from 'd3';
import elasticsearch from 'elasticsearch';


var sort = null;


const Status = React.createClass({

    // this.props.jobName + "/execution/" + this.props.executionId +
    getClient(){
        return new elasticsearch.Client({host: 'localhost:9200', log: 'error'});
    },
    getData(){
        var client = this.getClient();
        client.search({
            index: 'ansible_logs-*',
            type: 'ansible-runs',
            body: {
                "query": {
                    "bool": {
                        "must": [
                            {"match": {"executionId": this.props.executionId }},
                            {"match": {"jobName": this.props.jobName }}

                        ]
                    }
                },
                "aggs": {
                    "status": {
                        "terms": {"field": "status"}
                    }
                }
            }
        }).then(function (resp) {
            var pieData = [];
            resp.aggregations.status.buckets.map(function (item) {
                var i = {x: item.key, y: item.doc_count}
                pieData.push(i);
            })
            var data = {
                values: pieData
            };
            console.log(data);
            if (this.isMounted()) {
                    this.setState({data: data});
            }
        }.bind(this), function (err) {
            console.trace(err.message);
        });
    },

    getInitialState() {
        var data = {
            label: '', values: []
        };
        return {data: data};
    },
    componentDidMount(){
        this.getData();
    },
    render() {
        var colorScale = d3.scale.ordinal().domain(["ok", "failed", "changed", "skipped", "unreachable"])
            .range(["#31a354", "#e6550d", "#e7ba52", "#9ecae1", "#fd8d3c"]);

        var sort = null;
        return (
            <div>
                <PieChart
                    data={this.state.data}
                    width={600}
                    height={400}
                    margin={{top: 10, bottom: 10, left: 100, right: 100}}
                    sort={sort}
                    colorScale={colorScale}
                    />

            </div>
        );
    }
});

export default Status;