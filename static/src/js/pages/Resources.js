import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { resourcesURI } from '../utils/Config'
import { Link } from 'react-router';
import { Grid, Row, Col } from 'react-bootstrap';
import $ from 'jquery';


const Resources = React.createClass({
  loadData() {
    return $.getJSON(resourcesURI());
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
        <NavMain activePage="resources"/>
        <PageHeader title="Resources"/>

        <Grid>
          <Row>
            <div className="box box-success">
              <div className="box-header with-border">
                <h3 className="box-title">Resource List</h3>

                <div className="box-tools pull-right">
                  <Link to="/new/resource">
                    <button type="button" className="btn bg-olive btn-flat ion-plus"> Add Resource
                    </button>
                  </Link>
                </div>
              </div>

              <div className="box-body">
                <table className="table table-striped">
                  <tbody>
                  <tr>
                    <th>Resource Name</th>
                    <th>Type</th>
                    <th></th>
                    <th></th>
                  </tr>
                  {
                    this.state.data.map(function (row, i) {
                      return <tr>
                        <td>{row.name}</td>
                        <td>{row.type}</td>
                        <td>
                          <button type="button" className="btn bg-olive btn-flat ion-edit"> Edit</button>
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
          </Row>

        </Grid>

        <PageFooter />
      </div>
    );
  }
});

export default Resources;
