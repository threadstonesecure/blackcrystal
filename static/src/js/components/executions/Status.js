import React from 'react';

import {PieChart} from 'react-d3-components';
import d3 from 'd3';
import elasticsearch from 'elasticsearch';
import { elasticSearchHost, elasticSearchIndex } from '../../utils/Config';



var sort = null;


const Status = React.createClass({

    // this.props.jobName + "/execution/" + this.props.executionId +
    getClient(){
        return new elasticsearch.Client(elasticSearchHost());
    },
    getData(){
        var client = this.getClient();
        client.search({
            index: elasticSearchIndex(),
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

        var tooltipScatter = function(x, y) {
            return "x: " + x + " y: " + y;
        };

        var sort = null;
        return (
            <div>
                <PieChart
                    data={this.state.data}
                    width={600}
                    height={400}
                    tooltipHtml={tooltipScatter}
                    tooltipOffset={top: 10, left: 10}
                    colorScale={colorScale}
                    />

            </div>
        );
    }
});

export default Status;
