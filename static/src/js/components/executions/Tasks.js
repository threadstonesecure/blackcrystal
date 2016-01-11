import React from 'react';
import { Input, Row, Col, Grid, Table } from 'react-bootstrap';
import $ from 'jquery';
import elasticsearch from 'elasticsearch';
import { elasticSearchHost, elasticSearchIndex } from '../../utils/Config'


const Tasks = React.createClass({
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
                "size":0,
                "aggs": {
                    "tasks": {
                        "terms": {"field": "task"}
                    }
                }
            }
        }).then(function (resp) {
           this.setState({tasks: resp.aggregations.tasks.buckets});
        }.bind(this), function (err) {
            console.trace(err.message);
        });

    },

    getInitialState() {
        return { tasks: []};
    },

    componentDidMount(){
        this.getData();
    },
    render() {
        return (
             <div className="box">
                <div className="box-header with-border">
                    <h3 className="box-title">Executed Tasks</h3>
                </div>
                <div className="box-body">
                    <table className="table table-bordered">
                        <tbody>
                        <tr>
                            <th>#</th>
                            <th>Tasks</th>
                        </tr>
                        {
                        this.state.tasks.map(function (row,i) {
                            return <tr>
                            <td>{i + 1}.</td>
                                <td>{row.key}</td>

                            </tr>
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
});

export default Tasks;
