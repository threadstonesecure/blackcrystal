import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { jobsURI } from '../utils/Config'
import { Link } from 'react-router';
import prettyCron from 'prettycron';
import {Grid, Row, Col} from 'react-bootstrap';
import $ from 'jquery';

const Jobs = React.createClass({
  loadData() {
    return $.getJSON(jobsURI());
  },
  open() {
    if (window.confirm("Are you sure you want to delete this job?")) {
      console.log("ok");
    }
  },
  getInitialState() {
    return {data: []};
  },

  componentDidMount(){
    this.loadData().success(function (data) {
      if (this.isMounted()) {
        console.log(data)
        this.setState({data: data});
      }
    }.bind(this))
  },

  render() {
    return (
      <div>
        <NavMain activePage="jobs"/>
        <PageHeader title="Jobs"/>

        <Grid>
          <Row>
            <Col>
              <div className="box box-success">
                <div className="box-header with-border">
                  <h3 className="box-title">All Available Jobs</h3>
                  <div className="box-tools pull-right">
                    <Link to="/new/job">
                      <button type="button" className="btn bg-olive btn-flat ion-plus"> Add Job
                      </button>
                    </Link>
                  </div>

                </div>

                <div className="box-body">
                  <table className="table table-striped">
                    <tbody>
                    <tr>
                      <th>#</th>
                      <th>Job Name</th>
                      <th>Active</th>
                      <th>Next Execution time</th>
                      <th></th>
                      <th></th>
                      <th></th>
                    </tr>
                    {
                      this.state.data.map(function (row, i) {
                        return <tr>
                          <td>{i + 1}.</td>
                          <td>{row.name}</td>
                          <td>
                            <span className={row.enabled ==true ? "ion-happy" : "ion-sad"}></span>
                          </td>
                          <td>
                            {prettyCron.getNext(row.executionTime)}
                          </td>

                          <td>
                            <Link to={"/job/" + row.name+"/executions"}>
                              <button type="button" className="btn bg-olive btn-flat ion-ios-list-outline"> Executions
                              </button>
                            </Link>
                          </td>
                          <td>
                            <Link to={"/job/" + row.name}>
                              <button type="button" className="btn bg-olive btn-flat ion-edit"> Edit</button>
                            </Link>
                          </td>
                          <td>
                            <button type="button" className="btn  btn-flat ion-ios-trash"> Delete</button>
                          </td>
                        </tr>
                      })}
                    </tbody>
                  </table>
                </div>
              </div>
            </Col>
          </Row>

        </Grid>

        <PageFooter />
      </div>
    );
  }
});

export default Jobs;
