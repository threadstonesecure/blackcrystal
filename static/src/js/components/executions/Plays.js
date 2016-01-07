import React from 'react';
import { Input, Row, Col, Grid, Table } from 'react-bootstrap';
import $ from 'jquery';
import elasticsearch from 'elasticsearch';


const Plays = React.createClass({
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
                "size":0,
                "aggs": {
                    "plays": {
                        "terms": {"field": "play"}
                    }
                }
            }
        }).then(function (resp) {
           this.setState({plays: resp.aggregations.plays.buckets});
        }.bind(this), function (err) {
            console.trace(err.message);
        });

    },

    getInitialState() {
        return { plays: []};
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
                        <th>Plays</th>

                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.plays.map(function (row) {
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

export default Plays;
