import React from 'react';

import {PieChart} from 'react-d3-components';
import d3 from 'd3';
import elasticsearch from 'elasticsearch';
import { elasticSearchHost, elasticSearchIndex } from '../../utils/Config';



var sort = null;


const Status = React.createClass({
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
            resp.aggregations.status.buckets.map(function (item,i) {
                var element = {key:i, x: item.key, y: item.doc_count}
                pieData.push(element);
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
             <div className="box">
            <div className="box-header with-border">
              <h3 className="box-title">Task Execution Overview</h3>

              <div className="box-tools pull-right">
                <button type="button" className="btn btn-box-tool" data-widget="collapse"><i className="fa fa-minus"></i>
                </button>
                <button type="button" className="btn btn-box-tool" data-widget="remove"><i className="fa fa-times"></i></button>
              </div>
            </div>
            <div className="box-body">
              
                <PieChart
                    data={this.state.data}
                    width={600}
                    height={400}
                    tooltipHtml={tooltipScatter}
                    colorScale={colorScale}
                    />

             </div>
           </div>
        );
    }
});

export default Status;
