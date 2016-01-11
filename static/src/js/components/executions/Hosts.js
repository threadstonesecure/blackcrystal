import React from 'react';
import $ from 'jquery';
import elasticsearch from 'elasticsearch';
import { elasticSearchHost, elasticSearchIndex } from '../../utils/Config';

const Hosts = React.createClass({
    getClient(){
        return new elasticsearch.Client(elasticSearchHost());
    },

    getData(){
        var client = this.getClient();
        client.search({
            index: elasticSearchIndex(),
            type: 'ansible-stats',
            body: {
                "query": {
                    "bool": {
                        "must": [
                            {"match": {"executionId": this.props.executionId}},
                            {"match": {"jobName": this.props.jobName}}

                        ]
                    }
                }
            }
        }).then(function (resp) {
            this.setState({hosts: resp.hits.hits});
        }.bind(this), function (err) {
            console.trace(err.message);
        });

    },
    getInitialState() {
        return {hosts: []};
    },

    componentDidMount(){
        this.getData();
    },
    render() {
        return (
            <div className="box">
                <div className="box-header with-border">
                    <h3 className="box-title">Task Execution Summary By Host</h3>
                </div>

                <div className="box-body">
                    <table className="table table-bordered">
                        <tbody>
                        <tr>
                            <th>#</th>
                            <th>Host</th>
                            <th>Ok</th>
                            <th>Changed</th>
                            <th>Failed</th>
                            <th>Unreachable</th>
                        </tr>
                        {
                            this.state.hosts.map(function (row, i) {
                                return <tr>
                                    <td>{i + 1}.</td>
                                    <td>{row._source.hostname}</td>
                                    <td><span className={row._source.ok == 0 ? "badge" : "badge bg-green"}>{row._source.ok}</span>
                                    </td>
                                    <td><span
                                        className={row._source.changed == 0 ? "badge" : "badge bg-yellow"}>{row._source.changed}</span>
                                    </td>
                                    <td><span
                                        className={row._source.failed == 0 ? "badge" : "badge bg-red"}>{row._source.failed}</span>
                                    </td>
                                    <td><span
                                        className={row._source.unreachable == 0 ? "badge" : "badge bg-orange"}>{row._source.unreachable}</span>
                                    </td>
                                </tr>
                            })}
                        </tbody>
                    </table>
                </div>
            </div>

        );
    }
});

export default Hosts;
