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
            <div>

                <Table striped bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Tasks</th>

                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.tasks.map(function (row) {
                            return <tr>
                                <td>{row.key}</td>

                            </tr>
                        })}
                    </tbody>
                </Table>

            </div>
        );
    }
});

export default Tasks;
